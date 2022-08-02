package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.SetMealDish;

import java.util.List;

/**
 * @author HeYunjia
 */
public interface SetMealDishService extends IService<SetMealDish> {

    int removeBySetMealIdBatch(List<String> id);
}
