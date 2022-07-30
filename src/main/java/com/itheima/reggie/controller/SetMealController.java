package com.itheima.reggie.controller;

import com.itheima.reggie.dto.R;
import com.itheima.reggie.dto.SetMealDTO;
import com.itheima.reggie.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
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

    @GetMapping("page")
    public R<SetMealDTO> listSetMeal() {

        return R.error("改接口还没实现");
    }

    @DeleteMapping
    public R<String> remove(@RequestParam("ids") String ids) {

        return R.error("接口还没实现");
    }

}
