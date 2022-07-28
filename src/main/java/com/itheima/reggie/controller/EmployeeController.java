package com.itheima.reggie.controller;

import cn.hutool.core.net.Ipv4Util;
import com.alibaba.druid.support.http.util.IPAddress;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sun.net.util.IPAddressUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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


    /**
     * 员工退出
     *
     * @param request 使用 session, 清空对象
     * @return 返回成功信息
     */
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request) {

        return employeeService.logout(request);
    }


    @GetMapping("page")
    public R<List<Employee>> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        return null;
    }
}
