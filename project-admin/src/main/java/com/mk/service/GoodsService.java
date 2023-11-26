package com.mk.service;

import com.mk.bean.AjaxResult;

public interface GoodsService {

    AjaxResult decreaseStore(Integer userId, Integer goodsId);
}
