package com.mk;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyCustomPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycle")) {
            System.out.println("<<<<<<<<<<销毁之前执行，如@PreDestroy");
        }
    }

    /** 实例化 = new对象
     * 在new对象之前执行这个方法
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if(beanName.equals("lifeCycle")){
            System.out.println("<<<<<<<<<实例化之前执行，这里返回的对象会替换掉原本的bean");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycle")) {
            System.out.println("实例化之后，这里如果返回false会跳过依赖注入阶段");
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.println("===============================");
        if (beanName.equals("lifeCycle")) {
            System.out.println("<<<<<<<<<<依赖注入阶段，如@Autowired,@Value,@Resource");
        }
        return pvs;
    }

    /**
     * 初始化=给对象赋值
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycle")) {
            System.out.println("<<<<<<<<<<初始化之前执行，这里返回的对象会替换掉原本的bean，如@PostConstruct,@ConfigurationProperties");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycle")) {
            System.out.println("<<<<<<<<<<初始化之后执行，这里返回的对象会替换掉原本的bean，如代理增强");
        }
        return bean;
    }
}
