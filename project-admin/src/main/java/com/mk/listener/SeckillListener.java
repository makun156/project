package com.mk.listener;

import com.mk.service.GoodsService;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    //// TODO: 2023/11/26  分布式方案二：使用Redis分布式锁，来保证数据一致性问题
    @Override
    public void onMessage(MessageExt message) {
        String msg = new String(message.getBody());
        String[] msgBody = msg.split(":");
        Integer userId = Integer.valueOf(msgBody[0]);
        Integer goodsId = Integer.valueOf(msgBody[1]);
        RLock lock = redissonClient.getLock("lock:anyLock");
        try {
            boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);
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
