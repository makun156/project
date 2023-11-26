package com.mk.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 区
 */
@Data
@TableName("areas")
public class Area {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField(value = "area_id")
    private String areaId;
    @TableField(value = "area")
    private String area;
    @TableField(value = "city_id")
    private String cityId;
}
