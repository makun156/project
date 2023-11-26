package com.mk.service;

import com.baomidou.mybatisplus.extension.service.IService;

public interface RecordService extends IService<com.mk.bean.Record> {
    /**
     * 记录操作信息
     */
    void recordOperateInfo(com.mk.bean.Record operateInfo);
}
