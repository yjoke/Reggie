package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.SetMealDish;
import com.itheima.reggie.mapper.SetMealDishMapper;
import com.itheima.reggie.service.SetMealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author HeYunjia
 */

@Slf4j
@Service
public class SetMealDishServiceImpl extends ServiceImpl<SetMealDishMapper, SetMealDish>
        implements SetMealDishService {

    @Resource
    SetMealDishMapper setMealDishMapper;

    @Override
    public int removeBySetMealIdBatch(List<String> id) {

        return setMealDishMapper.delete(
                new QueryWrapper<SetMealDish>()
                    .lambda()
                    .in(SetMealDish::getSetMealId, id));

    }
}
