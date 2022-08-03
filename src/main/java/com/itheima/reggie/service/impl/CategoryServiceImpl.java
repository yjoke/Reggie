package com.itheima.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.CategoryDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.SetMeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetMealService;
import com.itheima.reggie.util.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author HeYunjia
 */

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private DishService dishService;

    @Resource
    private SetMealService setMealService;

    @Override
    public R<String> saveCategory(Category category) {

        if (!save(category)) R.error("未知错误, 添加失败");

        return R.success("添加成功");
    }

    @Override
    public R<Page<CategoryDTO>> listCategory(Integer page, Integer pageSize) {

        Page<CategoryDTO> pageInfo = new Page<>();

        Page<CategoryDTO> result = categoryMapper.selectPageDTO(pageInfo);

        /* MP 的日志包含了查询结果, 先注释掉了 */
        // log.info("返回信息: {}", JSONUtil.toJsonStr(result));
        return R.success(result);
    }

    @Override
    public R<String> modifyCategory(Category category) {

        if (!updateById(category)) return R.error("更新失败");

        return R.success("更新成功");
    }

    @Override
    public R<String> removeCategory(Long categoryId) {

        // 检查是否关联菜品
        Integer countDish = dishService.lambdaQuery()
                .eq(Dish::getCategoryId, categoryId)
                .count();
        if (countDish != null && countDish > 0) {
            throw new CustomException("当前分类下关联了菜品, 不可删除");
        }

        // 检查是否关联套餐
        Integer countSetMeal = setMealService.lambdaQuery()
                .eq(SetMeal::getCategoryId, categoryId)
                .count();
        if (countSetMeal != null && countSetMeal > 0) {
            throw new CustomException("当前分类下关联了套餐, 不可删除");
        }

        if (!removeById(categoryId)) return R.error("删除失败");

        return R.success("删除成功");
    }

    @Override
    public R<List<CategoryDTO>> listCategoryByType(Integer type) {

        List<Category> list = lambdaQuery()
                .eq(ObjectUtil.isNotNull(type), Category::getType, type)
                .orderByAsc(Category::getSort)
                .list();

        if (list == null || list.isEmpty())
            return R.success(Collections.emptyList());

        List<CategoryDTO> result = list.stream()
                .map(l -> BeanUtil.copyProperties(l, CategoryDTO.class))
                .collect(Collectors.toList());

        return R.success(result);
    }
}
