package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {

    @Resource
    private DishMapper dishMapper;

    @Override
    public R<Page<DishDTO>> listDish(Integer page, Integer pageSize, String dishName) {

        Page<DishDTO> pageInfo = new Page<>(page, pageSize);

        Page<DishDTO> result = dishMapper.selectDishDTO(pageInfo, dishName);

        return R.success(result);
    }
}
