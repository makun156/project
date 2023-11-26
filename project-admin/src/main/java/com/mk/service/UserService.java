package com.mk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mk.bean.AjaxResult;
import com.mk.bean.RegisterBody;
import com.mk.bean.Result;
import com.mk.bean.User;
import org.springframework.stereotype.Service;

public interface UserService extends IService<User> {
    User getUserName(String userId);

    AjaxResult login(User user);

    boolean getUserByPhone(String phone);

    boolean queryUser(String username);

    AjaxResult register(RegisterBody registerBody);
}
