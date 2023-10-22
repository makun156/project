package com.mk.service;

import com.mk.bean.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User getUserName(String userId);
}
