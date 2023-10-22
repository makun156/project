package com.mk.web;

import cn.hutool.core.bean.BeanUtil;
import com.mk.annotation.Log;
import com.mk.bean.AjaxResult;
import com.mk.bean.LoginBody;
import com.mk.bean.Result;
import com.mk.bean.User;
import com.mk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Log("测试切面")
    @PostMapping("login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        User user = BeanUtil.toBean(loginBody, User.class);
        return userService.login(user);
    }
    @GetMapping("{username}")
    public User getUser(@PathVariable("username") String username) {
        return userService.getUserName(username);
    }

}
