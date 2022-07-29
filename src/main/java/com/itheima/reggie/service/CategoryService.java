package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.CategoryDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Category;

/**
 * @author HeYunjia
 */
public interface CategoryService extends IService<Category> {

    R<String> saveCategory(Category category);

    R<Page<CategoryDTO>> listCategory(Integer page, Integer pageSize);

    R<String> modifyCategory(Category category);

    R<String> removeCategory(Long categoryId);
}
