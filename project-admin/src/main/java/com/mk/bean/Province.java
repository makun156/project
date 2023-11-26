package com.mk.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 省份
 */
@Data
@TableName("provinces")
public class Province {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("province_id")
    private String provinceId;
    @TableField("province")
    private String province;
    @TableField(exist = false)
    private List<City> cityList;
}
