package com.itheima.reggie.util.handler;

import com.itheima.reggie.dto.R;
import com.itheima.reggie.util.CustomException;
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
        log.error("拦截到 SQLIntegrityConstraintViolationException ");

        String errorMessage = e.getMessage();
        log.error(errorMessage);

        if (errorMessage.contains("Duplicate entry")) {
            String duplicateEntry = errorMessage
                    .substring(17, errorMessage.indexOf('\'', 17));
            return R.error(duplicateEntry + " 已存在");
        }

        return R.error("数据未知错误, 服务器异常");
    }

    /**
     * 处理业务逻辑异常
     *
     * @return 错误信息
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException e) {
        log.error("拦截到 CustomException - {}", e.getMessage());

        return R.error(e.getMessage());
    }
}
