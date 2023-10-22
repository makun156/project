package com.mk.intercept;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginIntercept implements HandlerInterceptor {
    private StringRedisTemplate redis;

    public LoginIntercept(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
        String authorization = request.getHeader("authorization");
        if (authorization==null) {
            throw new RuntimeException("未登录");
        }
        Object token = redis.opsForValue().get("login:token:" + authorization);
        System.out.println(token);
        return true;
	}
}
