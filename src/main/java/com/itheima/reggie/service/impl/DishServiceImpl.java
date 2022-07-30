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
}
