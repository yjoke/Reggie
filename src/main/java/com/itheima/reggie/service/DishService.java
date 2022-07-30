package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.vo.DishVO;

/**
 * @author HeYunjia
 */
public interface DishService extends IService<Dish> {

    R<Page<DishDTO>> listDish(Integer page, Integer pageSize, String dishName);

    R<String> modifyDishStatusBatch(Integer status, String ids);

    R<String> removeDishBatch(String ids);

    R<String> saveDish(DishVO dishVO);
}
