package com.itheima.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.util.CustomException;
import com.itheima.reggie.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author HeYunjia
 */

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {

    @Resource
    private DishMapper dishMapper;

    @Resource
    private DishFlavorService dishFlavorService;

    @Override
    public R<Page<DishDTO>> listDish(Integer page, Integer pageSize, String dishName) {

        Page<DishDTO> pageInfo = new Page<>(page, pageSize);

        Page<DishDTO> result = dishMapper.selectDishDTO(pageInfo, dishName);

        return R.success(result);
    }

    @Override
    public R<String> modifyDishStatusBatch(Integer status, String ids) {

        Object[] id = ids.split(",");

        boolean update = lambdaUpdate()
                .set(Dish::getStatus, status)
                .in(Dish::getId, id)
                .update();

        log.info("这个更新情况一直是 true...");
        log.info("更新情况: {}", update);

        return R.success("更新成功");
    }

    @Override
    @Transactional
    public R<String> removeDishBatch(String ids) {

        List<String> id = Arrays.stream(ids.split(",")).collect(Collectors.toList());

        Integer count = lambdaQuery()
                .eq(Dish::getStatus, 1)
                .in(Dish::getId, id)
                .count();

        if (count > 0) {
            throw new CustomException("删除前请先将菜品停售");
        }

        boolean flag = removeByIds(id);
        log.info("删除菜品 {}, 共 {} 个,  {}", id, id.size(), flag);

        int n = dishFlavorService.removeByDishIdBatch(id);
        log.info("删除对应口味共 {} 个", n);

        return R.success("删除成功");
    }

    @Override
    @Transactional
    public R<String> saveDish(DishVO dishVO) {

        Dish dish = BeanUtil.copyProperties(dishVO, Dish.class);

        save(dish);
        log.info("成功添加的菜品: {}", dish);

        List<DishFlavor> flavors = dishVO.getFlavors();
        flavors.forEach(flavor -> flavor.setDishId(dish.getId()));

        dishFlavorService.saveBatch(flavors);
        log.info("成功添加的菜品口味: {}", flavors);

        return R.success("新增成功");
    }

    @Override
    public R<DishVO> findDish(Long dishId) {

        Dish dish = lambdaQuery()
                .eq(Dish::getId, dishId)
                .one();

        List<DishFlavor> flavors = dishFlavorService.lambdaQuery()
                .eq(DishFlavor::getDishId, dishId)
                .list();

        DishVO dishVO = BeanUtil.copyProperties(dish, DishVO.class);

        dishVO.setFlavors(flavors);

        return R.success(dishVO);
    }

    @Override
    @Transactional
    public R<String> modifyDish(DishVO dishVO) {

        // 获取菜品信息部分, 更新
        Dish dish = BeanUtil.copyProperties(dishVO, Dish.class);
        updateById(dish);

        // 将旧的关联全部删掉
        dishFlavorService
                .removeByDishIdBatch(Collections
                        .singletonList(dish.getId().toString()));

        // 获取新的关联, 添加
        List<DishFlavor> flavors = dishVO.getFlavors();
        flavors.forEach(flavor -> flavor.setDishId(dish.getId()));
        dishFlavorService.saveBatch(flavors);

        return R.success("修改成功");
    }

    @Override
    public R<List<DishDTO>> listDishDTO(Long categoryId, String dishName, Integer status) {

        List<Dish> dishes = lambdaQuery()
                .eq(ObjectUtil.isNotNull(categoryId), Dish::getCategoryId, categoryId)
                .eq(ObjectUtil.isNotNull(status), Dish::getStatus, status)
                .like(StrUtil.isNotBlank(dishName), Dish::getName, dishName)
                .list();

        List<DishDTO> result = dishes.stream()
                .map(dish -> BeanUtil.copyProperties(dish, DishDTO.class))
                .collect(Collectors.toList());

        return R.success(result);
    }

}
