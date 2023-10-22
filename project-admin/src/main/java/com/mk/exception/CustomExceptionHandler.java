package com.mk.exception;

import com.mk.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.fail(e.getMessage());
    }
}
