package com.itheima.reggie.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author HeYunjia
 */

@Data
public class CategoryDTO {

    private Long id;

    // 类型 1 菜品分类 2 套餐分类
    private Integer type;

    private String name;

    private Integer sort;

    private LocalDateTime updateTime;

}
