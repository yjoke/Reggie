package com.itheima.reggie.vo;

import com.itheima.reggie.entity.DishFlavor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author HeYunjia
 */

@Data
public class DishVO {

    private static final long serialVersionUID = 1L;

    private Long id;


    //菜品名称
    private String name;


    //菜品分类id
    private Long categoryId;


    //菜品价格
    private BigDecimal price;


    //商品码
    private String code;


    //图片
    private String image;


    //描述信息
    private String description;


    //0 停售 1 起售
    private Integer status;

    List<DishFlavor> flavors;
}
