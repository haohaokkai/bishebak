package com.interceptor;

import com.exception.BusinessException;
import com.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(BusinessException.class)
    public R handleBusinessException(BusinessException e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handleOtherException(Exception e) {
        log.error("系统异常", e);
        return R.error("系统繁忙，请稍后再试");
    }
}