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

    /**
     * 按照 id 查找菜品
     *
     * @param dishId 菜品的 id
     * @return 返回菜品的相关信息
     */
    @GetMapping("{dishId}")
    public R<DishVO> findDish(@PathVariable("dishId") Long dishId) {
        log.info("要查询的菜品 id 为: {}", dishId);

        return dishService.findDish(dishId);
    }

    /**
     * 修改菜品信息
     *
     * @param dishVO 修改后的菜品信息
     * @return 修改是否成功
     */
    @PutMapping
    public R<String> modifyDish(@RequestBody DishVO dishVO) {
        log.info("修改的菜品信息: {}", dishVO);

        return dishService.modifyDish(dishVO);
    }

    /**
     * 按照菜品分类或者菜品名字获取菜品信息
     *
     * @param categoryId 分类 id
     * @param dishName 菜品名称
     * @param status 菜品状态
     * @return 返回该类的菜品
     */
    @GetMapping("list")
    public R<List<DishDTO>> listDishDTO(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "name", required = false) String dishName,
            @RequestParam(value = "status", required = false) Integer status) {
        log.info("要查询的菜品分类: {}, 菜品名称: {}", categoryId, dishName);

        return dishService.listDishDTO(categoryId, dishName, status);
    }
}
