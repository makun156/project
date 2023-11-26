package com.mk.service.imp;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.annotation.Record;
import com.mk.bean.AjaxResult;
import com.mk.bean.RegisterBody;
import com.mk.bean.User;
import com.mk.mapper.UserMapper;
import com.mk.service.UserService;
import com.mk.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        return user;
    }

    /**
     * 用户登录接口
     * @param user
     * @return
     */
    @Override
    @Record("登录接口")
    public AjaxResult login(User user) {
        User userData = userMapper.selectByUserName(user.getUsername());
        if (Objects.isNull(userData)) {
            throw new RuntimeException("当前用户不存在，请注册后登录！");
        }
        String token = JwtUtil.createToken(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone",user.getPhone());
        redis.opsForHash().putAll(LOGIN_TOKEN +token,map);
        redis.expire(LOGIN_TOKEN+token,TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        return AjaxResult.success("token",token);
    }

    /**
     * 通过手机号获取用户是否存在
     * @param phone
     * @return
     */
    @Override
    public boolean getUserByPhone(String phone) {
        User user = userMapper.getUserByPhone(phone);
        return user!=null;
    }

    @Override
    public boolean queryUser(String username) {
        User user = userMapper.selectByUserName(username);
        return user!=null;
    }

    @Override
    public AjaxResult register(RegisterBody registerBody) {
        if (getUserByPhone(registerBody.getPhone())) {
            throw new RuntimeException("当前手机号已被注册，请更换手机号！");
        }
        // TODO: 2023/11/1 修改
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();
        User user = new User();
        user.setPhone(registerBody.getPhone());
        user.setPassword(password);
        user.setType(2);
        user.setNickName(REGISTER_NICKNAME_PREFIX + RandomUtil.randomNumbers(8));
        user.setLoginTime(new Date());
        userMapper.insertUser(user);
        String token = JwtUtil.createToken(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        redis.opsForHash().putAll(LOGIN_TOKEN +token,map);
        redis.expire(LOGIN_TOKEN+token,TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        HashMap<String, Object> resData = new HashMap<>();
        resData.put("token",token);
        return AjaxResult.success("注册成功",resData);
    }
}
