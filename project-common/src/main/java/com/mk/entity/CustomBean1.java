package com.mk.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CustomBean1 {
    //@Lazy
    @Autowired
    private CustomBean2 customBean2;

    public CustomBean2 getCustomBean2() {
        return customBean2;
    }
}
