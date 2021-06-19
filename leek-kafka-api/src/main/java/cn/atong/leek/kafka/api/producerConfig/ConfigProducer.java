package cn.atong.leek.kafka.api.producerConfig;

import cn.atong.leek.kafka.api.KafkaConst;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

/**
 * @program: leek-kafka
 * @description: Producer 配置相关
 * @author: atong
 * @create: 2021-06-19 19:43
 */
public class ConfigProducer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        //Producer 必须指定的三个属性： bootstrap.servers; key.serializer; value.serializer
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConst.LOCAL_BROKER);
        properties.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , "org.apache.kafka.common.serialization.StringSerializer");
        //更多重要配置
        // acks
        properties.put("acks", "1");
        //一个批次可以使用的内存大小 默认16384(16k)
        properties.put("batch.size", 16384);
        //指定 生产者在发送批次前等待更多消息加入批次的时间  默认 0
        properties.put("linger.ms", 0L);
        //控制生产者发送请求大小, 默认1M.  这个参数需要和 kafka broker 的message.max.bytes一致
        properties.put("max.request.size", 1 * 1024 * 1024);

        //更多发送配置(非重要)
        // 生产者内存缓冲区大小
        properties.put("buffer.memory", 32 * 1024 * 1024L);
        // 重发消息次数
        properties.put("retries", Integer.MAX_VALUE);
        //客户端将等待请求的响应的最大时间 默认30秒
        properties.put("request.timeout.ms", 30 * 1000);
        // 最大阻塞时间, 超过则抛出异常 默认 60_000 ms
        properties.put("max.block.ms", 60 * 1000);
        //压缩类型, 默认无压缩, none/gzip/snappy
        properties.put("compression.type", "none");


        //阻塞之前,客户端在单个连接上发送的未确认请求的最大数目 max.in.flight.requests.per.connection 默认是 5
        // 若要保证生产顺序 需要把该值设为 1
        properties.put("max.in.flight.requests.per.connection", 1);
    }
}
