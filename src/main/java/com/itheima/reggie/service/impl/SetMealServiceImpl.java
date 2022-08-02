package com.itheima.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.dto.SetMealDTO;
import com.itheima.reggie.entity.SetMeal;
import com.itheima.reggie.entity.SetMealDish;
import com.itheima.reggie.mapper.SetMealMapper;
import com.itheima.reggie.service.SetMealDishService;
import com.itheima.reggie.service.SetMealService;
import com.itheima.reggie.vo.SetMealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HeYunjia
 */

@Slf4j
@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, SetMeal>
        implements SetMealService {

    @Resource
    private SetMealMapper setMealMapper;

    @Resource
    private SetMealDishService setMealDishService;

    @Override
    public R<Page<SetMealDTO>> listSetMeal(Integer page, Integer pageSize, String setMealName) {

        Page<SetMealDTO> pageInfo = new Page<>(page, pageSize);

        Page<SetMealDTO> result = setMealMapper.selectSetMealDTO(pageInfo, setMealName);

        return R.success(result);
    }

    @Override
    public R<String> modifySetMealStatusBatch(Integer status, String ids) {

        Object[] id = ids.split(",");

        boolean update = lambdaUpdate()
                .set(SetMeal::getStatus, status)
                .in(SetMeal::getId, id)
                .update();

        log.info("更新情况: {}", update);

        return R.success("更新成功");
    }

    @Override
    @Transactional
    public R<String> saveSetMeal(SetMealVO setMealVO) {

        SetMeal setMeal = BeanUtil.copyProperties(setMealVO, SetMeal.class);

        save(setMeal);
        log.info("添加套餐, {}", setMeal);

        List<SetMealDish> dishes = setMealVO.getSetmealDishes();
        dishes.forEach(dish -> dish.setSetMealId(setMeal.getId()));

        setMealDishService.saveBatch(dishes);
        log.info("添加套餐内菜品: {}", dishes);

        return R.success("添加成功");
    }

    @Override
    @Transactional
    public R<String> removeSetMealBatch(String ids) {

        List<String> id = Arrays.stream(ids.split(",")).collect(Collectors.toList());

        boolean flag = removeByIds(id);
        log.info("删除套餐信息数量: {}, {}", id.size(), flag);

        int n = setMealDishService.removeBySetMealIdBatch(id);
        log.info("删除套餐内菜品关联: {} 个", n);

        return R.success("删除成功");
    }
}
