package cn.atong.leek.kafka.api.concurrent;

import cn.atong.leek.kafka.api.KafkaConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: leek-kafka
 * @description: Producer 多线程Send
 * @author: atong
 * @create: 2021-06-19 13:34
 */
public class ConcurrentProducer {

    //并发发送消息的个数
    private static final int MSG_SIZE = 100;

    //负责发送消息的线程
    private static ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static CountDownLatch countDownLatch = new CountDownLatch(MSG_SIZE);


    private static User makeUser (int id) {
        User demoUser = new User(id, null);
        String  userName = "xiangxue_" + id;
        demoUser.setName(userName);
        return demoUser;
    }

    /**友送消息的任务*/
    private static class ProduceWorker implements Runnable{
        private ProducerRecord<String ,String> record ;
        private KafkaProducer<String , String> producer ;
        public ProduceWorker ( ProducerRecord<String,String> record, KafkaProducer<String,String> producer) {
            this.record = record;
            this.producer = producer;
        }

        @Override
        public void run() {
                final String id = Thread.currentThread().getId() + "_" + System.identityHashCode(producer);
                try {
                    producer.send(record, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            if (null != e) {
                                e.printStackTrace();
                            }
                            if (null != recordMetadata) {
                                System.out.println(id + "|" + "offset:" + recordMetadata.offset() + "-" + "partition:" + recordMetadata.partition());
                            }
                        }
                    });
                    System.out.println(id + ":数据[" + record + "]已发送。");
                    countDownLatch.countDown();
                }catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(
                KafkaConst.producerConfig(StringSerializer.class, StringSerializer.class));
        try {
            //循环发送，通过线程池的方式
            for (int i = 0; i < MSG_SIZE; i++) {
                User demoUser = makeUser(i);
                ProducerRecord<String, String> record =
                        new ProducerRecord<String, String>(KafkaConst.HELLO_TOPIC, null,
                                System.currentTimeMillis(), "hellokey", demoUser.toString());
                executorService.submit(new ProduceWorker(record, producer));
            }
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
            executorService.shutdown();
        }
    }
}


/**
 * @description  User Entity
 * @author atong
 * @date 19:43 2021/6/19
 * @version 1.0.0.1
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private Integer id;
    private String name;
}
