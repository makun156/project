package com.mk.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.mapper.RecordMapper;
import com.mk.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImp extends ServiceImpl<RecordMapper, com.mk.bean.Record> implements RecordService {
    @Autowired
    private RecordMapper operateMapper;
    @Override
    public void recordOperateInfo(com.mk.bean.Record operateInfo) {
        operateMapper.recordOperateInfo(operateInfo);
    }
}
