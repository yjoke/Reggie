package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.DishFlavor;

import java.util.List;

/**
 * @author HeYunjia
 */
public interface DishFlavorService extends IService<DishFlavor> {

    int removeByDishIdBatch(List<String> id);
}
