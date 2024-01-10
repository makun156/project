package com.mk.entity;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

public class Bean1 implements InitializingBean {

    public Bean1() {
    }
    public void init(){
        System.out.println("@bean注解执行initMethod方法的初始化方法执行");
    }

    @PostConstruct
    public void init2(){
        System.out.println("@PostConstruct注解的init方法执行");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("实现InitializingBean接口的初始化方法执行");
    }
}
