package com.mk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mk.bean.RegisterBody;
import com.mk.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserName(String userId);

    User selectByUserName(String username);

    User getUserByPhone(String phone);

    int insertUser(User user);
}
