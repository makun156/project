package com.mk.web;

import com.mk.bean.AjaxResult;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class SeckillController {
    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private RocketMQTemplate rocket;
    public static AtomicInteger count= new AtomicInteger();

    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;

    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("test.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }
    @PostMapping("seckill")
    public AjaxResult seckill(){
        //1.校验用户是否已经秒杀过当前商品
        //创建当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(new Date());
        Long executeResult = redis.execute(SECKILL_SCRIPT, Collections.emptyList(), "test:" + count.incrementAndGet() + ":10086:" + date, "1:10086");
        if (executeResult!=0L) {
            if (executeResult==1L) {
                System.out.println("已经购买过秒杀");
            }else if(executeResult==2L){
                System.out.println("秒杀商品库存不足");
            }
        }
        Message<String> message = MessageBuilder.withPayload(count + ":10086").build();
        rocket.asyncSend("seckillTopic", message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("消息发送失败,原因：{}", throwable.getMessage());
            }
        });
        return AjaxResult.success("正在拼命抢购中，请稍后去订单中心查看!!!");
    }

    //@PostMapping("seckill")
    //public AjaxResult seckill(){
    //    //1.校验用户是否已经秒杀过当前商品
    //    //创建当前时间
    //    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    //    String date = dateFormat.format(new Date());
    //
    //    Boolean flag = redis.opsForValue().setIfAbsent("test:"+count.incrementAndGet()+":10086:"+date,"1");
    //    if (!flag) {
    //        return AjaxResult.error("您已经秒杀过当前商品!");
    //    }
    //    //先查再改再更新，多线程下回导致超卖，数据不一致，线程不安全
    //    Long countStore = redis.opsForValue().decrement( "1:10086");
    //    if (countStore<0) return AjaxResult.error("当前秒杀商品库存不足!");
    //    Message<String> message = MessageBuilder.withPayload(count + ":10086").build();
    //    rocket.asyncSend("seckillTopic", message, new SendCallback() {
    //        @Override
    //        public void onSuccess(SendResult sendResult) {
    //            System.out.println("发送成功");
    //        }
    //
    //        @Override
    //        public void onException(Throwable throwable) {
    //            log.error("消息发送失败,原因：{}", throwable.getMessage());
    //        }
    //    });
    //    return AjaxResult.success("正在拼命抢购中，请稍后去订单中心查看!!!");
    //}
}
