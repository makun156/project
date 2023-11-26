package com.mk.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 市
 */
@Data
@TableName(value = "cities")
public class City {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField(value = "city_id")
    private String cityId;
    @TableField(value = "city")
    private String city;
    @TableField(value = "province_id")
    private String provinceId;
    @TableField(exist = false)
    private List<Area> areas;
}
