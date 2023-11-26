package com.mk.intercept;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWTUtil;
import com.mk.bean.User;
import com.mk.bean.UserDto;
import com.mk.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

import static com.mk.constant.Constant.*;
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
        Map<Object, Object> userMap = redis.opsForHash().entries(LOGIN_TOKEN + token);
        User userData = JwtUtil.parseToken(token);
        if (userMap.isEmpty()) {
            throw new RuntimeException("令牌过期，请重新登录！");
        }
        User user = BeanUtil.fillBeanWithMap(userMap, new User(), false);
        if (!Objects.equals(userData.getPhone(), user.getPhone())) {
            throw new RuntimeException("用户信息错误，请重新登录！");
        }
        return true;
    }
}
