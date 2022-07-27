package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HeYunjia
 */
public interface EmployeeService extends IService<Employee> {

    R<Employee> login(Employee employee, HttpServletRequest request);

    R<String> logout(HttpServletRequest request);
}
