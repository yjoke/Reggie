package com.itheima.reggie.controller;

import cn.hutool.json.JSONUtil;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.util.EmployeeIdHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    @PostMapping
    public R<String> saveCategory(@RequestBody Category category) {
        log.info("员工 {} 添加菜品信息 {}", EmployeeIdHolder.get(), JSONUtil.toJsonStr(category));

        return categoryService.saveCategory(category);
    }
}
