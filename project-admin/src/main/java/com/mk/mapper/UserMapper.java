package com.mk.mapper;

import com.mk.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
     User getUserName(String userId);
}
