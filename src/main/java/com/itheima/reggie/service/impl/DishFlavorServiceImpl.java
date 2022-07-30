package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishFlavorMapper;
import com.itheima.reggie.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HeYunjia
 */

@Slf4j
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
        implements DishFlavorService {

    @Resource
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public int removeByDishIdBatch(List<String> id) {

        return dishFlavorMapper.delete(
                new QueryWrapper<DishFlavor>()
                        .lambda()
                        .in(DishFlavor::getDishId, id));
    }
}
