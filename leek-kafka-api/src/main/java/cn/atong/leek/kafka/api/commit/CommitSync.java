package cn.atong.leek.kafka.api.commit;

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
 * @description: 手动提交-同步提交
 * @author: atong
 * @create: 2021-06-20 14:54
 */
public class CommitSync {
    public static void main(String[] args) {
        //消费者
        Properties properties =
                KafkaConst.consumerConfig("hellogroup", StringDeserializer.class, StringDeserializer.class);
        //取消自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        try {
            //消费者订阅主题, 可以订阅多个
            consumer.subscribe(Collections.singleton(KafkaConst.HELLO_TOPIC));
            while (true) {
                // 拉取消息
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("topic:%s,分区：%d,偏移量：%d," + "key:%s,value:%s",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                    //do business
                }
                //同步提交（会阻塞）
                consumer.commitSync();
            }
        }finally {
            consumer.close();
        }
    }
}
