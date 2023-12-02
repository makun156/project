package com.mk.listener;

import com.mk.service.GoodsService;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.mk.constant.Constant.REDIS_LOCK;
import static com.mk.constant.Constant.REDIS_LOCK_EXPIRE_TIME;

@Component
@RocketMQMessageListener(
        topic = "seckillTopic",
        consumeMode = ConsumeMode.CONCURRENTLY,
        consumeThreadNumber = 40,
        consumerGroup = "seckill-consumer-group")
public class SeckillListener implements RocketMQListener<MessageExt> {
    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private GoodsService goodsService;
    @Resource
    private RedissonClient redissonClient;
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;

    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("test.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    //// TODO: 2023/11/26  分布式方案二：使用Redis分布式锁，来保证数据一致性问题
    // TODO: 2023/11/27  设置等待时间短，bug 超卖，创建订单表244条，库存扣减300
    @Override
    public void onMessage(MessageExt message) {
        //String msg = new String(message.getBody());
        //String[] msgBody = msg.split(":");
        //Integer userId = Integer.valueOf(msgBody[0]);
        //Integer goodsId = Integer.valueOf(msgBody[1]);
        //Long luaResult = redis.execute(SECKILL_SCRIPT, Collections.emptyList(), userId.toString());
        //System.out.println(luaResult);
        //if (luaResult != 1) {
        //    return;
        //}
        //goodsService.decreaseStore(userId, goodsId);

        String msg = new String(message.getBody());
        String[] msgBody = msg.split(":");
        Integer userId = Integer.valueOf(msgBody[0]);
        Integer goodsId = Integer.valueOf(msgBody[1]);
        RLock lock = redissonClient.getLock("lock:anyLock");
        try {
            boolean isLock = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (isLock) {
                goodsService.decreaseStore(userId, goodsId);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
    //监听方法不会进行业务操作，也不会使用事物
    //// TODO: 2023/11/25  分布式方案一：使用数据库行锁，来保证数据一致性问题
    //@Override
    //public void onMessage(MessageExt message) {
    //    String msg = new String(message.getBody());
    //    String[] msgBody = msg.split(":");
    //    Integer userId = Integer.valueOf(msgBody[0]);
    //    Integer goodsId = Integer.valueOf(msgBody[1]);
    //    goodsService.decreaseStore(userId, goodsId);
    //}


    // TODO: 2023/11/25 问题复现2：在事务前加锁，只适合单机版，分布式版会出现问题
    //@Override
    //public void onMessage(MessageExt message) {
    //    String msg = new String(message.getBody());
    //    String[] msgBody = msg.split(":");
    //    Integer userId = Integer.valueOf(msgBody[0]);
    //    Integer goodsId = Integer.valueOf(msgBody[1]);
    //    synchronized (this) {
    //        goodsService.decreaseStore(userId, goodsId);
    //
    //    }
    //}
    // TODO: 2023/11/25 问题复现1：出现订单表和库存表数据不一致的情况
    //@Override
    //public void onMessage(MessageExt message) {
    //    String msg = new String(message.getBody());
    //    String[] msgBody = msg.split(":");
    //    Integer userId = Integer.valueOf(msgBody[0]);
    //    Integer goodsId = Integer.valueOf(msgBody[1]);
    //    synchronized (this) {
    //        goodsService.decreaseStore(userId, goodsId);
    //
    //    }
    //}
}
