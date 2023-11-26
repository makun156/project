package com.mk.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_user")
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long id;
    @TableField("user_name")
    private String username;
    @TableField("password")
    private String password;
    @TableField("nick_name")
    private String nickName;
    @TableField("phonenumber")
    private String phone;
    /**
     * 0：超级管理员
     * 1：管理员
     * 2：普通用户
     */
    @TableField("user_type")
    private Integer type;
    @TableField("email")
    private String email;
    @TableField("sex")
    private Integer sex;
    @TableField("login_time")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date loginTime;
}
