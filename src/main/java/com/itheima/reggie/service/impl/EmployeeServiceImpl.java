package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.itheima.reggie.constant.SessionConstant.EMPLOYEE_LOGIN_SESSION_KEY;

/**
 * @author HeYunjia
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
        implements EmployeeService {

    @Override
    public R<Employee> login(Employee employee, HttpServletRequest request) {
        // 1. 将密码加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. 根据用户名查询用户
        Employee emp = lambdaQuery()
                        .eq(Employee::getUsername, employee.getUsername())
                        .one();

        // 3. 判断是否为空
        if (emp == null) return R.error("登录失败");

        // 4. 密码比对
        if (!emp.getPassword().equals(password)) return R.error("登陆失败");

        // 5. 对比员工窗台
        if (emp.getStatus() == 0) return R.error("账号已被禁用");

        // 6. 登录成功, 将员工 id 存入 Session
        request.getSession()
                .setAttribute(EMPLOYEE_LOGIN_SESSION_KEY, emp.getId());

        // 7. 返回
        return R.success(emp);
    }

    @Override
    public R<String> logout(HttpServletRequest request) {
        request.removeAttribute(EMPLOYEE_LOGIN_SESSION_KEY);

        return R.success("退出成功");
    }
}
