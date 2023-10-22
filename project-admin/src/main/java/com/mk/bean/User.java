package com.mk.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class User {
    @TableField("user_id")
    private String  id;
    @TableField("user_name")
    private String name;
    @TableField("nick_name")
    private String nickName;
    @TableField("user_type")
    private Integer type;
    private String email;
    @TableField("phonenumber")
    private String phone;
    @TableField("sex")
    private Integer sex;
}
