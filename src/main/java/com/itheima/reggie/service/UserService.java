package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HeYunjia
 */
public interface UserService extends IService<User> {

    R<String> login(User user, HttpServletRequest request);
}
