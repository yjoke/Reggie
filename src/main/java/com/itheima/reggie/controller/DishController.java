package com.itheima.reggie.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.DishDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HeYunjia
 */

@Slf4j
@RestController
@RequestMapping("dish")
public class DishController {

    @Resource
    private DishService dishService;

    @GetMapping("page")
    public R<Page<DishDTO>> listDish(@RequestParam("page") Integer page,
                                     @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam(value = "name", required = false) String dishName) {
        log.info("分页查询, 第 {} 页, 数量 {}, 关键查询: {}", page, pageSize, dishName);

        return dishService.listDish(page, pageSize, dishName);
    }
}
