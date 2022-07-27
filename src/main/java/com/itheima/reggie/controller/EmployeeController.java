package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author HeYunjia
 */

@Slf4j
@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param employee 包含员工信息, 提供账号密码
     * @param request 使用 session
     * @return 返回 R<Employee>
     */
    @PostMapping("login")
    public R<Employee> login(
            @RequestBody Employee employee,
            HttpServletRequest request) {

        return employeeService.login(employee, request);
    }

}
