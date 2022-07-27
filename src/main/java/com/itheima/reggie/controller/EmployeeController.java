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

    @PostMapping("login")
    public R<Employee> login(
            @RequestBody Employee employee,
            HttpServletRequest request) {
        // 1. 将密码加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. 根据用户名查询用户
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 3. 判断是否为空
        if (emp == null) return R.error("登录失败");

        // 4. 密码比对
        if (!emp.getPassword().equals(password)) return R.error("登陆失败");

        // 5. 对比员工窗台
        if (emp.getStatus() == 0) return R.error("账号已被禁用");

        // 6. 登录成功, 将员工 id 存入 Session
        request.getSession().setAttribute("employee", emp.getId());

        // 7. 返回
        return R.success(emp);
    }

}
