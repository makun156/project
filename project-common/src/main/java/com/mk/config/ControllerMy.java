package com.mk.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerMy {
    @GetMapping("test")
    public String test() {
        System.out.println("test接口被调用");
        return null;
    }
}
