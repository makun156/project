package com.mk.service.imp;

import com.mk.bean.User;
import com.mk.mapper.UserMapper;
import com.mk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserName(String userId) {
        User user = userMapper.getUserName(userId);
        System.out.println(user);
        return user;
    }
}
