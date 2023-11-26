package com.mk.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonCustomConfig {
    @Bean
    public RedissonClient redissonConfig(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://101.43.183.177:6379").setPassword("mk123456");
        return Redisson.create(config);
    }
}
