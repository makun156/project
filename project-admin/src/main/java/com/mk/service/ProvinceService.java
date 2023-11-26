package com.mk.service;

import com.mk.bean.AjaxResult;
import com.mk.bean.Area;
import com.mk.bean.City;
import com.mk.bean.Province;

import java.util.List;

/**
 * 省市区查询
 */
public interface ProvinceService {
    /**
     * 查询所有省份
     * @return
     */
    List<Province> queryProvinces();

    /**
     * 查询省下面的城市
     * @param provinceId
     * @return
     */
    List<City> queryCity(String provinceId);

    /**
     * 查询所有城市
     * @return
     */
    List<City> queryCities();

    /**
     * 查询所有区县
     * @return
     */
    List<Area> queryAreas();
    /**
     * 查询省市区三级联动
     * @return
     */
    AjaxResult queryAll();
}
