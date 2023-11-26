package com.mk.web;

import cn.hutool.core.bean.BeanUtil;
import com.mk.bean.AjaxResult;
import com.mk.bean.LoginBody;
import com.mk.bean.User;
import com.mk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * @param loginBody
     * @return
     */
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
