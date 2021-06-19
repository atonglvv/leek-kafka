package cn.atong.leek.kafka.api.selfSerial;

import cn.atong.leek.kafka.api.domain.User;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @program: leek-kafka
 * @description: 自定义反序列化
 * @author: atong
 * @create: 2021-06-19 21:32
 */
public class SeflDeserializer implements Deserializer<User> {

    public void configure(Map<String,?> configs, boolean isKey) {}

    @Override
    public User deserialize(String s, byte[] bytes) {
        try {
            if (null == bytes) {
                return null;
            }
            if (bytes.length < 8) {
                throw new SerializationException("Error data size.");
            }
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            int id;
            String name;
            int nameSize;
            id = buffer.getInt();
            nameSize = buffer.getInt();
            byte[] nameByte = new byte[nameSize];
            buffer.get(nameByte);
            name = new String(nameByte, "UTF-8");
            return new User(id, name);
        } catch (Exception e) {
            throw new SerializationException("Error Deserializer User. " + e);
        }
    }

    public void close() {}
}
