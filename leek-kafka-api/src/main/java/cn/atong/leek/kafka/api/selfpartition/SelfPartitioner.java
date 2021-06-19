package cn.atong.leek.kafka.api.selfpartition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

/**
 * @program: leek-kafka
 * @description: 自定义分区器
 * @author: atong
 * @create: 2021-06-19 21:58
 */
public class SelfPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //拿到分区数
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        //分区数
        int num =  partitionInfos.size();
        //根据value与分区数求余的方式得到分区ID
        return ((String)value).hashCode() % num;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
