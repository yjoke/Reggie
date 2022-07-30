package com.itheima.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.vo.DishVO;
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
    public R<String> removeDishBatch(String ids) {

        List<String> id = Arrays.stream(ids.split(",")).collect(Collectors.toList());

        boolean delete = removeByIds(id);

        log.info("删除情况: {}", delete);

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
}
