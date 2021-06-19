package cn.atong.leek.kafka.api.selfpartition;

import cn.atong.leek.kafka.api.KafkaConst;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @program: leek-kafka
 * @description: 使用自定义分区器的生产者 - 同步发送
 * @author: atong
 * @create: 2021-06-19 22:16
 */
public class SelfPartitonProducer {

    private static KafkaProducer<String,String> producer = null;

    public static void main(String[] args) {
        // 构造Properties
        Properties properties = KafkaConst.producerConfig(StringSerializer.class, StringSerializer.class);
        // 自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "cn.atong.leek.kafka.api.selfpartition.SelfPartitioner");
        //生产者
        producer = new KafkaProducer<String,String>(properties);
        try {
            ProducerRecord<String,String> record;
            try {
                record = new ProducerRecord<String, String>(KafkaConst.HELLO_TOPIC, "hellokey", "futureTest");
                Future<RecordMetadata> future = producer.send(record);
                System.out.println("============");
                //阻塞在下面位置
                RecordMetadata recordMetadata = future.get();
                if (null != recordMetadata) {
                    System.out.println("offset:" + recordMetadata.offset() + "-" + "partition:" + recordMetadata.partition());
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }finally {
            producer.close();
        }
    }
}
