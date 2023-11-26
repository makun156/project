package com.mk.bean;

import lombok.Data;

@Data
public class RegisterBody {
    // 1 用户用户名注册 2 手机号注册
    private Integer type;
    //用户名
    private String username;
    //手机号
    private String phone;
    //密码
    private String password;
    //验证码
    private String code;
}
