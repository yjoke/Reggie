package com.itheima.reggie.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.CategoryDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public R<String> saveCategory(Category category) {

        if (!save(category)) R.error("未知错误, 添加失败");

        return R.success("添加成功");
    }

    @Override
    public R<Page<CategoryDTO>> listCategory(Integer page, Integer pageSize) {

        Page<CategoryDTO> pageInfo = new Page<>();

        Page<CategoryDTO> result = categoryMapper.selectPageDTO(pageInfo);

        log.info("返回信息: {}", JSONUtil.toJsonStr(result));
        return R.success(result);
    }

    @Override
    public R<String> modifyCategory(Category category) {

        if (!updateById(category)) return R.error("更新失败");

        return R.success("更新成功");
    }

    @Override
    public R<String> removeCategory(Long categoryId) {

        if (!removeById(categoryId)) return R.error("删除失败");

        return R.success("删除成功");
    }
}
