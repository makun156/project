package com.mk;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ProjectAdminApplicationTests {

    @Test
    void contextLoads() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test-producer");
        //设置注册中心
        producer.setNamesrvAddr("192.168.111.128:9876");
        producer.start();
        Message message = new Message("orderTopic", "订单".getBytes(StandardCharsets.UTF_8));
        SendResult result = producer.send(message);
        System.out.println(result.getSendStatus());
        producer.shutdown();
    }

    @Test
    void tset() {
        int[] nums={3,2,2,3};
        System.out.println(removeDuplicates(nums,3));
    }

    public static int removeDuplicates(int[] nums,int val) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]==val) nums[i]=-1;
        }
        int[] newNum=new int[nums.length];
        int index=0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != -1) {
                newNum[index]=nums[i];
                index++;
            }
        }
        for (int i = 0; i < newNum.length; i++) {
            nums[i]=newNum[i];
        }
        return index+1;
    }

}
