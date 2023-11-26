package com.mk.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 记录用户的操作日志
 */
@Data
@TableName("sys_record_info")
public class Record {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    @TableField
    private String username;
    @TableField
    private String message;
    @TableField
    private Date operateTime;
    @TableField
    private String ipAddr;
}
