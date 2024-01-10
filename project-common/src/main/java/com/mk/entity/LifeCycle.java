//package com.mk.entity;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
//@Component
//public class LifeCycle {
//    @Autowired
//    private Component1 component1;
//    public LifeCycle() {
//        System.out.println("第一个执行");
//        System.out.println("无参构造方法执行");
//    }
//    @Autowired
//    public void autowired(@Value("${JAVA_HOME}") String home) {
//        System.out.println("第二个执行");
//        System.out.println("autowired注入方法执行");
//        System.out.println("依赖注入java_home的值为===>"+home);
//    }
//    @PostConstruct
//    public void init() {
//        System.out.println("第三个执行");
//        System.out.println("init初始化方法执行");
//    }
//    @PreDestroy
//    public void destroy() {
//        System.out.println("第四个执行，项目结束执行");
//        System.out.println("destroy销毁方法执行");
//    }
//}
