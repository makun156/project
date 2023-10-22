package com.mk.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class User {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long  id;
    @TableField("user_name")
    private String username;
    @TableField("password")
    private String password;
    @TableField("nick_name")
    private String nickName;
    @TableField("phonenumber")
    private String phone;
    @TableField("user_type")
    private Integer type;
    @TableField("email")
    private String email;
    @TableField("sex")
    private Integer sex;
}
