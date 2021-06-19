package cn.atong.leek.kafka.api.sendtype;

import cn.atong.leek.kafka.api.KafkaConst;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.concurrent.Future;

/**
 * @program: leek-kafka
 * @description: Kafka Producer 异步发送
 * @author: atong
 * @create: 2021-06-19 13:10
 */
public class AsynProducer {

    private static KafkaProducer<String,String> producer = null;

    public static void main(String[] args) {
        //消息生产者
        producer = new KafkaProducer<String,String>(
                KafkaConst.producerConfig(StringSerializer.class, StringSerializer.class));

        ProducerRecord<String,String> record;
        try {
            record = new ProducerRecord<String, String>(KafkaConst.HELLO_TOPIC, "hellokey", "AsynSendTest");
            //异步发送
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                    if (null != recordMetadata) {
                        System.out.println("offset:" + recordMetadata.offset() + "-" + "partition:" + recordMetadata.partition());
                    }
                }
            });
        }finally {
            producer.close();
        }
    }
}
