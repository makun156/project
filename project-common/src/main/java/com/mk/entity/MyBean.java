package com.mk.entity;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

public class MyBean implements BeanNameAware, InitializingBean {
    public MyBean() {
        System.out.println("MyBean的无参构造执行");
    }

    //在初始化之前会回调Aware的接口实现类
    @Override
    public void setBeanName(String name) {
        System.out.println("我是实现了BeanNameAware接口的Bean，名字叫："+name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("我是实现了InitializingBean接口的初始化方法");
    }
}
