package com.mk.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.bean.AjaxResult;
import com.mk.bean.Goods;
import com.mk.mapper.GoodsMapper;
import com.mk.service.GoodsService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class GoodsServiceImp extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private GoodsMapper goodsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult decreaseStore(Integer userId, Integer goodsId) {
        // TODO: 2023/11/25  分布式方案二：使用Redis分布式锁，来保证数据一致性问题
        Goods goods = goodsService.selectById(1);
        int finalStore = Integer.valueOf(goods.getStore()) - 1;
        if (finalStore < 0) throw new RuntimeException("商品库存不足!");
        goods.setStore(finalStore + "");
        int i = goodsService.updateById(goods);
        if (i > 0) {
            System.out.println("更新订单表");
        }

        return null;
    }
    /**
     * 扣减库存
     * 写订单表
     *
     * @param userId
     * @param goodsId
     */
    //@Override
    //@Transactional(rollbackFor = Exception.class)
    //public AjaxResult decreaseStore(Integer userId, Integer goodsId) {
    //    // TODO: 2023/11/25  使用数据库行锁（使用并发不大的场景下，使用数据库行锁），来保证数据一致性问题
    //    //当更新数据时，开启事务后，使用 update tableName set xx=xx+1 where id=xx;
    //    //若没有提交事务，其他会阻塞更新数据库，直到当前事务提交后，其他事务才会执行
    //    int i = goodsService.updateByIdLineLock(goodsId);
    //    if (i == 0) return AjaxResult.error("当前商品库存不足!");
    //    System.out.println("更新订单表");
    //    return AjaxResult.success("扣减库存成功!");
    //}
    // TODO: 2023/11/25 问题复现2：在事务前加锁，只适合单机版，分布式版会出现问题
    //@Override
    //@Transactional(rollbackFor = Exception.class)
    //public void decreaseStore(Integer userId, Integer goodsId) {
    //    Goods goods = goodsService.selectById(1);
    //    int finalStore = Integer.valueOf(goods.getStore()) - 1;
    //    if (finalStore < 0) throw new RuntimeException("商品库存不足!");
    //    goods.setStore(finalStore + "");
    //    int i = goodsService.updateById(goods);
    //    if (i > 0) {
    //        System.out.println("更新订单表");
    //    }
    //}
    // TODO: 2023/11/25  : 2023/11/25 问题复现1：出现订单表和库存表数据不一致的情况
    ///**
    // * 扣减库存
    // * 写订单表
    // *
    // * @param userId
    // * @param goodsId
    // */
    //@Override
    //@Transactional(rollbackFor = Exception.class)
    //// TODO: 2023/11/25  出现数据不一致问题：订单表有100条数据，但是库存只扣减了50条，导致订单表和库存不一致
    ////当线程A抢到锁时，执行扣减库存操作，此时线程B在阻塞，当线程A修改完库存释放锁后，
    //// 还未提交事务，线程B抢到锁，读取到的库存量还是线程A修改之前的库存量，因为线程A还未提交事务，数据库的库存还未更新导致的问题
    //public void decreaseStore(Integer userId, Integer goodsId) {
    //    int i=0;
    //    synchronized (this){
    //        Goods goods = goodsService.selectById(1);
    //        int finalStore = Integer.valueOf(goods.getStore()) - 1;
    //        if (finalStore < 0) throw new RuntimeException("商品库存不足!");
    //        goods.setStore(finalStore + "");
    //        i = goodsService.updateById(goods);
    //    }
    //    if (i > 0) {
    //        System.out.println("更新订单表");
    //    }
    //}

}
