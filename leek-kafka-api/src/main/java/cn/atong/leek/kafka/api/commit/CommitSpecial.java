package cn.atong.leek.kafka.api.commit;

import cn.atong.leek.kafka.api.KafkaConst;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: leek-kafka
 * @description: 自定义提交（特殊提交）
 * @author: atong
 * @create: 2021-06-20 15:41
 */
public class CommitSpecial {
    public static void main(String[] args) {
        //消费者
        Properties properties =
                KafkaConst.consumerConfig("hellogroup", StringDeserializer.class, StringDeserializer.class);
        //取消自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        Map<TopicPartition, OffsetAndMetadata> currOffsets = new HashMap<>();
        int count = 0;
        try {
            //消费者订阅主题, 可以订阅多个
            consumer.subscribe(Collections.singleton(KafkaConst.HELLO_TOPIC));
            while (true) {
                // 拉取消息
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("topic:%s,分区：%d,偏移量：%d," + "key:%s,value:%s",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                    currOffsets.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset()+1, "no meta"));
                    if (count % 11 == 0) {
                        // 特定提交（异步方式, 加入偏移量）, 每消费11条消息提交一次
                        consumer.commitAsync(currOffsets, null);
                    }
                    count++;
                }
                //异步提交
                consumer.commitAsync();
            }
        }catch (CommitFailedException e) {
            System.out.println("Commit Failed.");
            e.printStackTrace();
        }finally {
            try {
                //确保万无一失, finally 同步提交
                consumer.commitSync();
            }finally {
                consumer.close();
            }
        }
    }
}
