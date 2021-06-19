package cn.atong.leek.kafka.api.selfSerial;

import cn.atong.leek.kafka.api.domain.User;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @program: leek-kafka
 * @description: 自定义序列化器
 * @author: atong
 * @create: 2021-06-19 21:17
 */
public class SelfSerializer implements Serializer<User> {

    public void configure(Map<String,?> configs, boolean isKey) {}

    @Override
    public byte[] serialize(String s, User user) {
        try {
            byte[] name;
            int nameSize;
            if (null == user) {
                return null;
            }
            if (null != user.getName()) {
                name = user.getName().getBytes("utf-8");
                //字符串长度
                nameSize = user.getName().length();
            }else {
                name = new byte[0];
                nameSize = 0;
            }
            /*  id 的长度4个字节,
                字符串name 描述 4个字节
                字符串本身长度 nameSize 个字节
             */
            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + nameSize);
            //4
            buffer.putInt(user.getId());
            //4
            buffer.putInt(nameSize);
            //nameSize
            buffer.put(name);
            return buffer.array();
        } catch (Exception e) {
            throw  new SerializationException("Error serialize User : " + e);
        }
    }

    public void close() {}

}
