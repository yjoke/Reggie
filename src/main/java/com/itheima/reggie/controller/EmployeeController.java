package com.itheima.reggie.controller;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.http.util.IPAddress;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * @return 返回 R<Employee>
     */
    @PostMapping("login")
    public R<Employee> login(
            @RequestBody Employee employee,
            HttpServletRequest request) {
        log.info("登录, 登录账号: {}", employee.getUsername());

        return employeeService.login(employee, request);
    }

    /**
     * 员工退出
     *
     * @return 返回成功信息
     */
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request) {
        log.info("登出");

        return employeeService.logout(request);
    }

    /**
     * 新增员工
     *
     * @param employee 员工信息, 包括 (账号, 姓名, 手机号, 性别, 身份证号)
     * @return 返回成功失败信息
     */
    @PostMapping
    public R<String> saveEmployee(
            @RequestBody Employee employee,
            HttpServletRequest request) {
        log.info("新增员工, 前端传来员工信息: {}", JSONUtil.toJsonStr(employee));

        return employeeService.saveEmployee(employee, request);
    }


    /**
     * 查询员工列表
     *
     * @param page 页码
     * @param pageSize 每页大小
     * @param employeeName 查询的员工姓名
     * @return 返回分页数据
     */
    @GetMapping("page")
    public R<Page<Employee>> listEmployee(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String employeeName) {
        log.info("分页查询, 第 {} 页, 数量 {}, 关键查询: {}", page, pageSize, employeeName);

        return employeeService.listEmployee(page, pageSize, employeeName);
    }


    /**
     * 修改员工信息
     *
     * @param employee 员工信息
     * @return 修改成功信息
     */
    @PutMapping
    public R<String> modifyEmployee(
            @RequestBody Employee employee,
            HttpServletRequest request) {
        log.info("要修该的信息为: {}", JSONUtil.toJsonStr(employee));

        return employeeService.modifyEmployee(employee, request);
    }


    @GetMapping("{employeeId}")
    public R<Employee> findEmployee(@PathVariable("employeeId") Long employeeId) {
        log.info("查找员工: {}", JSONUtil.toJsonStr(employeeId));

        return employeeService.findEmployee(employeeId);
    }
}
