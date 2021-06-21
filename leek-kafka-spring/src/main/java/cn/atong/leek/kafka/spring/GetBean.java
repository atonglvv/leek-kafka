package cn.atong.leek.kafka.spring;

import cn.atong.leek.kafka.spring.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: leek-kafka
 * @description:
 * @author: atong
 * @create: 2021-06-21 21:21
 */
public class GetBean {
    public static void main(String[] args) {
        //查询类路径 加载配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //根据id获取bean
        //Spring就是一个大工厂（容器）专门生成bean bean就是对象
        User user = (User) applicationContext.getBean("User");
        //输出获取到的对象
        System.out.println("user = " + user);
    }
}
