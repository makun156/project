package com.mk;

import com.mk.config.Config;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            ComponentScan componentScan = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
            //通过这个类获取每一个资源类的元数据信息
            CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
            if (componentScan!= null) {
                for (String s : componentScan.basePackages()) {
                    String str= "classpath*:"+s.replace(".", "/")+"/**/*.class";
                    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(str);
                    AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();
                    for (Resource resource : resources) {
                        //获取这个包下的类的元数据
                        MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
                        //如果这个类资源直接获取间接添加了Component注解，则直接获取这个类，将这个类添加到beanFactory中
                        if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName()) || metadataReader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName())) {
                            //手动生成一个bean定义
                            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(metadataReader.getClassMetadata().getClassName()).getBeanDefinition();
                            if(beanFactory instanceof DefaultListableBeanFactory){
                                DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
                                String generateBeanName = generator.generateBeanName(beanDefinition, factory);
                                factory.registerBeanDefinition(generateBeanName, beanDefinition);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
