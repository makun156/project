package com.mk;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class RegisterEventPub extends ApplicationEvent {
    public RegisterEventPub(Object source) {
        super(source);
    }
}
