package com.itheima.reggie.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.CategoryDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.util.LoginAccountHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
        log.info("员工 {} 添加菜品信息 {}", LoginAccountHolder.get(), JSONUtil.toJsonStr(category));

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

    /**
     * 删除分类信息
     *
     * @param categoryId 要删除的 id
     * @return 返回删除是否成功
     */
    @DeleteMapping
    public R<String> removeCategory(@RequestParam("ids") Long categoryId) {
        log.info("要删除的分类 id: {}", categoryId);

        return categoryService.removeCategory(categoryId);
    }

    /**
     * 根据 type 查询分类
     *
     * @param type 分类类型
     * @return 返回分类集合
     */
    @GetMapping("list")
    public R<List<CategoryDTO>> listCategoryByType(
            @RequestParam(value = "type", required = false) Integer type) {
        log.info("要查询的类型为: {}", type);

        return categoryService.listCategoryByType(type);
    }
}
