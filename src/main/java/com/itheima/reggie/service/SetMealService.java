package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.dto.SetMealDTO;
import com.itheima.reggie.entity.SetMeal;
import com.itheima.reggie.vo.SetMealVO;

/**
 * @author HeYunjia
 */
public interface SetMealService extends IService<SetMeal> {

    R<Page<SetMealDTO>> listSetMeal(Integer page, Integer pageSize, String setMealName);

    R<String> modifySetMealStatusBatch(Integer status, String ids);

    R<String> saveSetMeal(SetMealVO setMealVO);

    R<String> removeSetMealBatch(String ids);
}
