package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.DishDTO;
import com.itheima.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author HeYunjia
 */

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    Page<DishDTO> selectDishDTO(@Param("page") Page<DishDTO> pageInfo,
                                @Param("dishName") String dishName);
}
