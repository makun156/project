package com.mk;

import com.mk.config.Config;
import com.mk.config.MyRequestMappingHandlerMappingAdapter;
import com.mk.entity.Bean1;
import com.mk.entity.CustomBean1;
import com.mk.entity.MyBean;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.CompletableFuture;

//@SpringBootApplication
public class ProjectCommonApplication {
    public static void main(String[] args) throws Exception{
        AnnotationConfigServletWebServerApplicationContext webServerApplicationContext = new AnnotationConfigServletWebServerApplicationContext(Config.class);
        //作用：解析@RequestMapping，以及派生注解，生成路径与控制器方法的映射关系，在初始化就会生成
        RequestMappingHandlerMapping bean = webServerApplicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取映射结果
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        //请求来了，获取控制器方法，返回处理器执行链对象
        HandlerExecutionChain get = bean.getHandler(new MockHttpServletRequest("GET", "/test"));
        MyRequestMappingHandlerMappingAdapter handlerMappingAdapter = webServerApplicationContext.getBean(MyRequestMappingHandlerMappingAdapter.class);
        //去调用控制器方法
        handlerMappingAdapter.invokeHandlerMethod(new MockHttpServletRequest("GET", "/test"), new MockHttpServletResponse(), (HandlerMethod) get.getHandler());
        System.out.println(1);
    }

}
