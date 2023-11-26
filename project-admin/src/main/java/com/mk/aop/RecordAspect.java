package com.mk.aop;

import cn.hutool.log.Log;
import com.mk.bean.User;
import com.mk.service.RecordService;
import com.mk.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class RecordAspect {
    @Autowired
    private RecordService operateService;
    @Before(value = "@annotation(record)")
    public void beforeVerity(JoinPoint joinPoint, com.mk.annotation.Record record){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        User user = (User) joinPoint.getArgs()[0];
        String ip = IpUtil.getIpAddr(request);
        com.mk.bean.Record operate = new com.mk.bean.Record();
        operate.setIpAddr(ip);
        operate.setOperateTime(new Date());
        operate.setUsername(user.getUsername());
        operate.setMessage(record.value());
        operateService.recordOperateInfo(operate);
    }
}
