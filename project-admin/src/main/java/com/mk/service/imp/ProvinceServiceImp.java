package com.mk.service.imp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mk.bean.AjaxResult;
import com.mk.bean.Area;
import com.mk.bean.City;
import com.mk.bean.Province;

import com.mk.mapper.ProvinceMapper;
import com.mk.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mk.constant.Constant.*;

/**
 *
 */
@Service
public class ProvinceServiceImp implements ProvinceService {
    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private ProvinceMapper provinceMapper;

    /**
     * 查询所有省份
     *
     * @return
     */
    @Override
    public List<Province> queryProvinces() {
        return provinceMapper.queryProvinces();
    }

    /**
     * 根据省份id查询城市
     *
     * @param provinceId
     * @return
     */
    @Override
    public List<City> queryCity(String provinceId) {
        return provinceMapper.queryCitiesByProvince(provinceId);
    }

    @Override
    public List<City> queryCities() {
        return provinceMapper.queryCities();
    }

    @Override
    public List<Area> queryAreas() {
        return provinceMapper.queryAreas();
    }

    /**
     * 省市区三级联动查询
     *
     * @return
     */
    @Override
    public AjaxResult queryAll() {
        long start = System.currentTimeMillis();
        String provinceCache = redis.opsForValue().get(PROVINCE_CITY_AREA);
        if (StrUtil.isNotBlank(provinceCache)) {
            return AjaxResult.success(provinceCache);
        }
        List<Province> provinces = queryProvinces();
        List<City> cities = queryCities();
        List<Area> areas = queryAreas();
        for (Province province : provinces) {
            List<City> cityList = new ArrayList<>();
            String provinceId = province.getProvinceId();
            for (City city : cities) {
                String cityId = city.getCityId();
                List<Area> areaList = new ArrayList<>();
                for (Area area : areas) {
                    String supCityId = area.getCityId();
                    if (Objects.equals(cityId, supCityId)) areaList.add(area);
                }
                city.setAreas(areaList);
                String supProId = city.getProvinceId();
                if (Objects.equals(provinceId, supProId)) cityList.add(city);
            }
            province.setCityList(cityList);
        }
        long end = System.currentTimeMillis();
        System.out.println("省市区三级联动耗时:"+(end-start));
        redis.opsForValue().set(PROVINCE_CITY_AREA, provinces.toString());
        return AjaxResult.success(provinces);
    }
}
