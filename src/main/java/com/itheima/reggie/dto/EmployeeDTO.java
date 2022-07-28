package com.itheima.reggie.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author HeYunjia
 */

@Data
public class EmployeeDTO {

    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    /**
     * 身份证
     */
    private String idNumber;

    private Integer status;

}
