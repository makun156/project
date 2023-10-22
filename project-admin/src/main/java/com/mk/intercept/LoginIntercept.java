package com.mk.intercept;

import cn.hutool.core.bean.BeanUtil;
import com.mk.bean.User;
import com.mk.bean.UserDto;
import com.mk.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
        String token = request.getHeader("authorization");
        if (token == null) {
            throw new RuntimeException("未登录！");
        }
        Map<Object, Object> userMap = redis.opsForHash().entries("login:token:" + token);
        User userData = JwtUtil.parseToken(token);
        if (userMap.isEmpty()) {
            throw new RuntimeException("令牌过期，请重新登录！");
        }
        UserDto userDto = BeanUtil.fillBeanWithMap(userMap, new UserDto(), false);
        if (!userDto.getPassword().equals(userData.getPassword()) || !userDto.getUsername().equals(userData.getUsername())) {
            throw new RuntimeException("用户信息错误，请重新登录！");
        }
        return true;
    }
}
