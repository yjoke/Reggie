package com.itheima.reggie.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author HeYunjia
 */

@Data
public class SetMealDTO {

    private Long id;

    //分类id
    private String categoryName;

    //套餐名称
    private String name;


    //套餐价格
    private BigDecimal price;

    //状态 0:停用 1:启用
    private Integer status;

    private LocalDateTime updateTime;

    //图片
    private String image;
}
