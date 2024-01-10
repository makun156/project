package com.mk;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RegisterEventReceive {
    /**
     * 监听RegisterEventPub这个类的事件
     * @param event
     */
    @EventListener
    public void receive(RegisterEventPub event) {
        //event获取事件的源对象
        System.out.println(event);
    }
}
