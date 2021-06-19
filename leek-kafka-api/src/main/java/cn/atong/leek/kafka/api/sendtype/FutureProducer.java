package cn.atong.leek.kafka.api.sendtype;

import cn.atong.leek.kafka.api.KafkaConst;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.concurrent.Future;

/**
 * @program: leek-kafka
 * @description: 生产者-同步发送
 * @author: atong
 * @create: 2021-06-16 22:51
 */
public class FutureProducer {

    private static KafkaProducer<String,String> producer = null;

    public static void main(String[] args) {
        //生产者
        producer = new KafkaProducer<String,String>(KafkaConst.producerConfig(StringSerializer.class, StringSerializer.class));
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
