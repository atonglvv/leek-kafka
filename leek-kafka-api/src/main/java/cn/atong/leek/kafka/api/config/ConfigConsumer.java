package cn.atong.leek.kafka.api.config;

import cn.atong.leek.kafka.api.KafkaConst;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RangeAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

/**
 * @program: leek-kafka
 * @description: Consumer 相关配置
 * @author: atong
 * @create: 2021-06-19 22:44
 */
public class ConfigConsumer {

    public static void main(String[] args) {
        //Consumer 必须指定以下三个参数：
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConst.LOCAL_BROKER);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        //更多消费者配置(重要的)
        // 消费者在读取一个没有偏移量的分区或者偏移量无效的情况下, 如何处理   latest/earliest/none
        properties.put("auto.offset.reset", "latest");
        //消费者是否自动提交偏移量 默认值为true
        properties.put("enable.auto.commit", true);
        //每次poll方法 返回的记录数  默认为500
        properties.put("max.poll.records", 500);
        // 分区分配给消费者的策略  默认Range   Range/roundRobin
        properties.put("partition.assignment.strategy", Collections.singletonList(RangeAssignor.class));
    }
}
