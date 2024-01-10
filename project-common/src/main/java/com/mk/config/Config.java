package com.mk.config;

import com.mk.entity.Bean1;
import com.mk.entity.Component1;
import com.mk.entity.Component2;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@ComponentScan()
public class Config {
    //内嵌web容器工厂
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }
    //创建DispatcherServlet
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
    //注册DispatcherServlet,SpringMVC的入口
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean() {
        //path：哪些路径会被DispatcherServlet处理
        return new DispatcherServletRegistrationBean(dispatcherServlet(), "/");
    }
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }
    @Bean
    public MyRequestMappingHandlerMappingAdapter myRequestMappingHandlerMappingAdapter() {
        return new MyRequestMappingHandlerMappingAdapter();
    }
}
