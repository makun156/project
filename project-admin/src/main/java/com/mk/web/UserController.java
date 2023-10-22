package com.mk.web;

import com.mk.bean.Result;
import com.mk.bean.User;
import com.mk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("login")
    public String login(){
        User user = new User();
        user.setName("成功");
        return "成功";
    }
    @GetMapping("getUser/{id}")
    public User getUser(@PathVariable("id")String id){
        return userService.getUserName(id);
    }

}
