package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.SetMealDTO;
import com.itheima.reggie.entity.SetMeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author HeYunjia
 */

@Mapper
public interface SetMealMapper extends BaseMapper<SetMeal> {

    Page<SetMealDTO> selectSetMealDTO(@Param("pageInfo") Page<SetMealDTO> pageInfo,
                                      @Param("setMealName") String setMealName);
}
