package com.mk.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 商品表
 */
@Data
@TableName("goods_store")
public class Goods {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("goods_id")
    private String goodsId;
    @TableField("store")
    private String store;
}
