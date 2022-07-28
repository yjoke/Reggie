package com.itheima.reggie.util.handler;

import com.itheima.reggie.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author HeYunjia
 */

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler {

    /**
     * 处理数据库的约束规范
     *
     * @return 异常返回
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {

        String errorMessage = e.getMessage();
        log.error(errorMessage);

        if (errorMessage.contains("Duplicate entry")) {
            return R.error("账号已存在");
        }

        return R.error("数据未知错误, 服务器异常");
    }
}
