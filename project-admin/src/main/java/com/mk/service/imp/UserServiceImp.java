package com.mk.service.imp;

import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.bean.AjaxResult;
import com.mk.bean.Result;
import com.mk.bean.User;
import com.mk.constant.Constant;
import com.mk.mapper.UserMapper;
import com.mk.service.UserService;
import com.mk.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.mk.constant.Constant.*;

@Service
public class UserServiceImp extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserName(String userId) {
        User user = userMapper.getUserName(userId);
        System.out.println(user);
        return user;
    }

    @Override
    public AjaxResult login(User user) {
        User userData = userMapper.selectByUserName(user.getUsername());
        if (Objects.isNull(userData)) {
            throw new RuntimeException("当前用户不存在，请注册后登录！");
        }
        String token = JwtUtil.createToken(user);
        String name = userData.getUsername();
        String password = userData.getPassword();
        HashMap<String, Object> map = new HashMap<>();
        map.put("username",name);
        map.put("password",password);
        redis.opsForHash().putAll(REDIS_LOGIN_TOKEN +token,map);
        redis.expire(REDIS_LOGIN_TOKEN+token,REDIS_TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        return AjaxResult.success("token",token);
    }
}
