package com.mk;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class ProjectAdminApplicationTests {

    @Test
    void contextLoads() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("test-producer");
        //设置注册中心
        producer.setNamesrvAddr("192.168.111.128:9876");
        producer.start();
        Message message = new Message("orderTopic", "订单".getBytes(StandardCharsets.UTF_8));
        SendResult result = producer.send(message);
        System.out.println(result.getSendStatus());
        producer.shutdown();
    }

}
