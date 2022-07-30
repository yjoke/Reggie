package com.itheima.reggie.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.DishDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */

@Slf4j
@RestController
@RequestMapping("dish")
public class DishController {

    @Resource
    private DishService dishService;

    /**
     * 分页查找
     *
     * @param page 页码
     * @param pageSize 页大小
     * @param dishName 需要模糊查询的菜品名字
     * @return 返回菜品管理页面需要的信息
     */
    @GetMapping("page")
    public R<Page<DishDTO>> listDish(@RequestParam("page") Integer page,
                                     @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam(value = "name", required = false) String dishName) {
        log.info("分页查询, 第 {} 页, 数量 {}, 关键查询: {}", page, pageSize, dishName);

        return dishService.listDish(page, pageSize, dishName);
    }

    /**
     * 批量修改菜品的状态
     *
     * @param status 要修改为状态
     * @param ids 要批量修改的菜品信息
     * @return 返回修改成功
     */
    @PostMapping("status/{status}")
    public R<String> modifyDishStatusBatch(@PathVariable("status") Integer status,
                                      @RequestParam("ids") String ids) {
        log.info("状态改为 {} 的 id: {}", status == 1 ? "启售" : "停售", ids);

        return dishService.modifyDishStatusBatch(status, ids);
    }

    /**
     * 批量删除菜品
     * TODO 先搞一个新增再说删除, 这个删除应该还要同步删除菜品和口味的关系
     *
     * @param ids 要批量删除的 id
     * @return 返回删除成功
     */
    @DeleteMapping
    public R<String> removeDishBatch(@RequestParam("ids") String ids) {
        log.info("删除的 id: {}", ids);

        return dishService.removeDishBatch(ids);
    }


    /**
     * 新增菜品
     *
     * @param dishVO 菜品信息, 包括菜品信息和口味
     * @return 返回新增是否成功
     */
    @PostMapping
    public R<String> saveDish(@RequestBody DishVO dishVO) {
        log.info("要添加的菜品信息: {}", JSONUtil.toJsonStr(dishVO));

        return dishService.saveDish(dishVO);
    }
}
