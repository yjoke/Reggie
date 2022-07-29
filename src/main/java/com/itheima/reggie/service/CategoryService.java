package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Category;

/**
 * @author HeYunjia
 */
public interface CategoryService extends IService<Category> {

    R<String> saveCategory(Category category);
}
