package com.mk.service;

import com.mk.bean.AjaxResult;

public interface GoodsService {

    default AjaxResult decreaseStore(Integer userId, Integer goodsId){return null;};
}
