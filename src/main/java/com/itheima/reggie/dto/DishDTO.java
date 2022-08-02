package com.itheima.reggie.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author HeYunjia
 */

@Data
public class DishDTO {

    private Long id;

    //菜品名称
    private String name;

    //图片
    private String image;

    //菜品分类
    private String categoryName;

    //菜品价格
    private BigDecimal price;

    //描述信息
    private String description;

    //0 停售 1 起售
    private Integer status;


    private LocalDateTime updateTime;
}
