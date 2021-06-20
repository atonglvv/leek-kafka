package cn.atong.leek.kafka.api.concurrent;

import cn.atong.leek.kafka.api.KafkaConst;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: leek-kafka
 * @description: Consumer 并发拉取
 * @author: atong
 * @create: 2021-06-20 10:27
 */
public class ConcurrentConsumer {

    public static final int CONCURRENT_PARTITIONS_COUNT = 10;

    public static ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_PARTITIONS_COUNT);

    private static class ConsumerWorker implements Runnable {

        private KafkaConsumer<String,String> consumer;
        //使用KafkaConsumer的实例要小心，每个消费数据的线程应该拥有自己的KafkaConsumer实例
        public ConsumerWorker(Map<String,Object> config, String topic) {
            Properties properties = new Properties();
            properties.putAll(config);
            //一个线程一个消费者 因为消费者是线程不安全的
            this.consumer = new KafkaConsumer<String, String>(properties);
            consumer.subscribe(Collections.singletonList(topic));
        }

        @Override
        public void run() {
            final String id = Thread.currentThread().getId() + "-" + System.identityHashCode(consumer);

            try{
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                    for (ConsumerRecord<String,String> record : records) {
                        System.out.println(String.format(id + " topic:%s,分区：%d,偏移量：%d," + "key:%s,value:%s",
                                record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                    }
                }
            } finally {
                consumer.close();
            }

        }
    }


    public static void main(String[] args) {
        Map<String, Object> config = KafkaConst.consumerConfigMap("concurrent", StringDeserializer.class, StringDeserializer.class);
        for (int i = 0; i < CONCURRENT_PARTITIONS_COUNT; i++) {
            executorService.submit(new ConsumerWorker(config, KafkaConst.HELLO_TOPIC));
        }
    }
}
