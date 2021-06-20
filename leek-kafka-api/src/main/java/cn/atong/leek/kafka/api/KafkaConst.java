package cn.atong.leek.kafka.api;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.Map;
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
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCAL_BROKER);
        properties.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , keySerializeClazz);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , valueSerializeClazz);
        return properties;
    }

    public static Map<String, Object> consumerConfigMap(String groupId,
                                                        Class<StringDeserializer> keyStringSerializer,
                                                        Class<StringDeserializer> valueStringSerializer) {
        Map<String,Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConst.LOCAL_BROKER);
        map.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyStringSerializer);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueStringSerializer);
        return map;
    }
}
