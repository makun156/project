package com.mk.bean;

/**
 * 账号密码登录对象
 */
public class LoginBody {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户手机号
     */
    private String phoneNumber;
    /**
     * 验证码
     */
    private String code;
    /**
     * 登录类型
     * 1:用户名密码登录
     * 2:手机号验证码登录
     */
    private Integer type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}