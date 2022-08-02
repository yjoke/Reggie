package com.itheima.reggie.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.dto.SetMealDTO;
import com.itheima.reggie.service.SetMealService;
import com.itheima.reggie.vo.SetMealVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */

@Slf4j
@RestController
@RequestMapping("setmeal")
public class SetMealController {

    @Resource
    private SetMealService setMealService;

    /**
     * 分页查找
     *
     * @param page 页码
     * @param pageSize 页大小
     * @param setMealName 查询的套餐名字
     * @return 返回页面展示信息
     */
    @GetMapping("page")
    public R<Page<SetMealDTO>> listSetMeal(@RequestParam("page") Integer page,
                                           @RequestParam("pageSize") Integer pageSize,
                                           @RequestParam(value = "name", required = false) String setMealName) {
        log.info("分页查询, 第 {} 页, 数量 {}, 关键查询: {}", page, pageSize, setMealName);

        return setMealService.listSetMeal(page, pageSize, setMealName);
    }

    /**
     * 批量修改套餐的售卖状态
     *
     * @param status 要改为的状态
     * @param ids 要修改的 id
     * @return 修改成功
     */
    @PostMapping("status/{status}")
    public R<String> modifySetMealStatusBatch(@PathVariable("status") Integer status,
                                              @RequestParam("ids") String ids) {
        log.info("状态改为 {} 的 id: {}", status == 1 ? "启售" : "停售", ids);

        return setMealService.modifySetMealStatusBatch(status, ids);
    }

    /**
     * 添加套餐
     *
     * @param setMealVO 新增的套餐信息
     * @return 返回添加成功
     */
    @PostMapping
    public R<String> saveSetMeal(@RequestBody SetMealVO setMealVO) {
        log.info("要填加的套餐信息： {}", JSONUtil.toJsonStr(setMealVO));

        return setMealService.saveSetMeal(setMealVO);
    }

    /**
     * 批量删除套餐
     *
     * @param ids 要批量删除的 id
     * @return 返回删除成功
     */
    @DeleteMapping
    public R<String> removeSetMealBatch(@RequestParam("ids") String ids) {
        log.info("要批量删除的套餐 id: {}", ids);

        return setMealService.removeSetMealBatch(ids);
    }

    /**
     * 按照 id 查询套餐
     *
     * @param setMealId 套餐 id
     * @return 返回查询到的套餐信息
     */
    @GetMapping("{setMealId}")
    public R<SetMealVO> findSetMeal(@PathVariable("setMealId") Long setMealId) {
        log.info("查找的套餐 id: {}", setMealId);

        return setMealService.findSetMeal(setMealId);
    }

    /**
     * 按照 id 修改套餐信息
     *
     * @param setMealVO 修改后的套餐信息
     * @return 修改成功
     */
    @PutMapping
    public R<String> modifySetMeal(@RequestBody SetMealVO setMealVO) {
        log.info("要修改的信息: {}", JSONUtil.toJsonStr(setMealVO));

        return setMealService.modifySetMeal(setMealVO);
    }
}
