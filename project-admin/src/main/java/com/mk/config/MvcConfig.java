package com.mk.config;

import com.mk.intercept.LoginIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public final String[] excludePath={"/user/code","/user/login","/register/**","/**"};
    @Autowired
    private StringRedisTemplate redis;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginIntercept(redis)).excludePathPatterns(excludePath).order(2);
        //WebMvcConfigurer.super.addInterceptors(registry);
    }

}
