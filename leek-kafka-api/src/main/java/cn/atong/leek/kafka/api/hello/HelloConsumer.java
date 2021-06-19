package cn.atong.leek.kafka.api.hello;

import cn.atong.leek.kafka.api.KafkaConst;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @program: leek-kafka
 * @description: Hello Consumer
 * @author: atong
 * @create: 2021-06-16 21:09
 */
public class HelloConsumer {

    public static void main(String[] args) {
        //Consumer 必须指定三个参数：
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);
        // group.id 并非完全必须指定
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "hellogroup");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        try {
            //消费者订阅主题, 可以订阅多个
            consumer.subscribe(Collections.singleton(KafkaConst.HELLO_TOPIC));
            while (true) {
                // 拉取消息
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> recode : records) {
                    System.out.println(String.format("topic:%s,分区：%d,偏移量：%d," + "key:%s,value:%s",
                            recode.topic(), recode.partition(), recode.offset(), recode.key(), recode.value()));
                }
            }
        }finally {
            consumer.close();
        }
    }
}
