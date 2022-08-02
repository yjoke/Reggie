package com.itheima.reggie.vo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.itheima.reggie.entity.SetMealDish;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author HeYunjia
 */

@Data
public class SetMealVO {

    private Long id;

    // 分类id
    private Long categoryId;


    // 套餐名称
    private String name;


    // 套餐价格
    private BigDecimal price;


    // 状态 0:停用 1:启用
    private Integer status;


    // 编码
    private String code;


    // 描述信息
    private String description;


    // 图片
    private String image;

    // 菜
    private List<SetMealDish> setmealDishes;
}
