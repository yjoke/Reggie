package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HeYunjia
 */
public interface EmployeeService extends IService<Employee> {

    R<Employee> login(Employee employee, HttpServletRequest request);

    R<String> logout(HttpServletRequest request);

    R<String> saveEmployee(Employee employee, HttpServletRequest request);

    R<Page<Employee>> listEmployee(Integer page, Integer pageSize, String employeeName);
}
