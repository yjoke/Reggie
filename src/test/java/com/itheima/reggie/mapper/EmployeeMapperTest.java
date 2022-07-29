package com.itheima.reggie.mapper;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */

@SpringBootTest
class EmployeeMapperTest {

    @Resource
    EmployeeMapper employeeMapper;

    @Test
    void selectPageVO() {
        System.out.println(employeeMapper);

        Page<EmployeeDTO> page = new Page<>();
        String employeeName = "李四";

        Page<EmployeeDTO> dtoPage = employeeMapper.selectPageDTO(page, null);

        System.out.println(JSONUtil.toJsonStr(dtoPage));
    }
}