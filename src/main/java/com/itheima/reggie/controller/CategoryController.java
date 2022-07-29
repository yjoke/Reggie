package com.itheima.reggie.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.CategoryDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.util.EmployeeIdHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

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

    /**
     * 新增分类信息
     *
     * @param category 分类信息
     * @return 返回是否成功
     */
    @PostMapping
    public R<String> saveCategory(@RequestBody Category category) {
        log.info("员工 {} 添加菜品信息 {}", EmployeeIdHolder.get(), JSONUtil.toJsonStr(category));

        return categoryService.saveCategory(category);
    }

    /**
     * 查询所有分类
     *
     * @param page 页码
     * @param pageSize 大小
     * @return 返回分页数据
     */
    @GetMapping("page")
    public R<Page<CategoryDTO>> listCategory(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize) {
        log.info("分页查询, 第 {} 页, 数量 {}", page, pageSize);

        return categoryService.listCategory(page, pageSize);
    }

    /**
     * 更新分类信息
     *
     * @param category 页面
     * @return 返回是否成功
     */
    @PutMapping
    public R<String> modifyCategory(@RequestBody Category category) {
        log.info("更新分类信息: {}", category);

        return categoryService.modifyCategory(category);
    }


    @DeleteMapping
    public R<String> removeCategory(@RequestParam("ids") Long categoryId) {
        log.info("要删除的分类 id: {}", categoryId);

        return categoryService.removeCategory(categoryId);
    }
}