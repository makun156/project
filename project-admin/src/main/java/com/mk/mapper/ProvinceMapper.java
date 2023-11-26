package com.mk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mk.bean.Area;
import com.mk.bean.City;
import com.mk.bean.Province;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProvinceMapper extends BaseMapper<Province> {
    /**
     * 查询所有省份
     * @return
     */
    List<Province> queryProvinces();

    /**
     * 通过省查询城市
     * @param provinceId
     * @return
     */
    List<City> queryCitiesByProvince(String provinceId);

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
}
