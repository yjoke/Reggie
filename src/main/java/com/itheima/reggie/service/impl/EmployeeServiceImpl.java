package com.itheima.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.EmployeeDTO;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.itheima.reggie.util.constant.SessionConstant.EMPLOYEE_ID;

/**
 * @author HeYunjia
 */

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
        implements EmployeeService {

    @Resource
    EmployeeMapper employeeMapper;

    /**
     * 默认密码: 123456, 已加密
     */
    private static final String DEFAULT_PW = "e10adc3949ba59abbe56e057f20f883e";

    @Override
    public R<EmployeeDTO> login(Employee employee, HttpServletRequest request) {
        // 1. 将密码加密
        String password = employee.getPassword();
        password = DigestUtil.md5Hex(password.getBytes());

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
        request.getSession().setAttribute(EMPLOYEE_ID, emp.getId());

        // 转换为 DTO 对象
        EmployeeDTO employeeDTO = toDTO(emp);
        log.info("员工登录成功: {}", employeeDTO.toString());
        // 7. 返回
        return R.success(employeeDTO);
    }

    @Override
    public R<String> logout(HttpServletRequest request) {
        Object employeeId = request.getSession().getAttribute(EMPLOYEE_ID);
        request.removeAttribute(EMPLOYEE_ID);

        log.info("员工 {} 退出", employeeId);
        return R.success("退出成功");
    }

    @Override
    public R<String> saveEmployee(Employee employee) {

        // 默认密码
        employee.setPassword(DEFAULT_PW);

        /* 更新前设置了 MP 自动填充公共字段 */
        if (!save(employee)) R.error("未知错误, 添加失败");

        log.info("新增员工成功, 新增员工信息为: {}", employee.toString());
        return R.success("新增员工成功");
    }

    @Override
    public R<Page<EmployeeDTO>> listEmployee(Integer page, Integer pageSize, String employeeName) {

        // 分页构造器
        Page<EmployeeDTO> pageInfo = new Page<>(page, pageSize);

        // 查询
        Page<EmployeeDTO> result = employeeMapper.selectPageDTO(pageInfo, employeeName);

        /* MP 的日志包含了查询结果, 先注释掉了 */
        // log.info("返回数据内容: {}", JSONUtil.toJsonStr(result));
        return R.success(result);
    }

    @Override
    public R<String> modifyEmployee(Employee employee) {

        /* 更新前设置了 MP 自动填充公共字段 */
        if (!updateById(employee)) {
            return R.error("员工信息修改失败");
        } else {
            return R.success("员工信息修改成功");
        }
    }

    @Override
    public R<EmployeeDTO> findEmployee(Long employeeId) {

        Employee employee = lambdaQuery()
                .eq(Employee::getId, employeeId)
                .one();

        if (employee == null) return R.error("不存在该员工");

        EmployeeDTO employeeDTO = toDTO(employee);

        log.info("查找到的用户信息为: {}", JSONUtil.toJsonStr(employeeDTO));
        return R.success(employeeDTO);
    }

    /**
     * 将 employee 对象为 employeeDTO
     *
     * @param employee Employee 对象
     * @return EmployeeDTO 对象
     */
    private EmployeeDTO toDTO(Employee employee) {
        return BeanUtil.copyProperties(employee, EmployeeDTO.class);
    }

}
