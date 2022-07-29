package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.CategoryDTO;
import com.itheima.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author HeYunjia
 */

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    Page<CategoryDTO> selectPageDTO(@Param("page") Page<CategoryDTO> page);

}
