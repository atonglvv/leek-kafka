package cn.atong.leek.kafka.api;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;

/**
 * @program: leek-kafka
 * @description: Kafka 构造方法
 * @author: atong
 * @create: 2021-06-16 23:00
 */
public class KafkaConst {

    public static String LOCAL_BROKER = "127.0.0.1:9092";

    public static final String HELLO_TOPIC = "HelloTopic";

    public static Properties producerConfig(
            Class<? extends Serializer> keySerializeClazz,
            Class<? extends Serializer> valueSerializeClazz) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,LOCAL_BROKER);
        properties.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , keySerializeClazz);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , valueSerializeClazz);
        return properties;

    }
}
