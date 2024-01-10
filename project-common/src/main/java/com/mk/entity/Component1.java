package com.mk.entity;

import com.mk.abstractClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class Component1{
    private Component2 component2;

    public Component1() {
        System.out.println("component1初始化了");
    }

    @Autowired
    public Component1(Component2 component2) {
        System.out.println(1);
        this.component2 = component2;
    }

    public Component2 getComponent2() {
        System.out.println(2);
        return component2;
    }

}
