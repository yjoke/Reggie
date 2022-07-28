package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.EmployeeDTO;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author HeYunjia
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    Page<EmployeeDTO> selectPageVO(@Param("page") Page<EmployeeDTO> page,
                                   @Param("employeeName") String employeeName);

}
