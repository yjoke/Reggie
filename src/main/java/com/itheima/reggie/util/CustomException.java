package com.itheima.reggie.util;

/**
 * 自定义业务异常
 *
 * @author HeYunjia
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
