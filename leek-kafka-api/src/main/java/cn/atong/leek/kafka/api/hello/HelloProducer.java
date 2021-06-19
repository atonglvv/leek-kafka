package cn.atong.leek.kafka.api.hello;

import cn.atong.leek.kafka.api.KafkaConst;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @program: leek-kafka
 * @description: Hello Producer 最简单的 Kafka Producer
 * @author: atong
 * @create: 2021-06-16 20:09
 */
public class HelloProducer {

    public static void main(String[] args) {
        //Producer 必须指定的三个属性： bootstrap.servers; key.serializer; value.serializer
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);

        try{
            ProducerRecord<String,String> record;
            try{
                //发送四条消息
                for (int i = 0; i < 6; i++) {
                    record = new ProducerRecord<String, String>(KafkaConst.HELLO_TOPIC, String.valueOf(i), "hello kafka");
                    //三种发送方式之一 : 简单发送, 发送即忘, 会有重试
                    producer.send(record);
                    System.out.println("发送第" + i + "条消息...");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }finally {
            producer.close();
        }

    }
}
