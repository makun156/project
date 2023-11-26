package com.mk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mk.bean.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    /**
     * 记录操作信息
     * @param operateInfo
     */
    void recordOperateInfo(Record operateInfo);
}
