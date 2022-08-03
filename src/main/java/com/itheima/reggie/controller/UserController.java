package com.itheima.reggie.controller;

import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 获取手机验证码
     *
     * @param user 用户信息, 里面包含手机号
     * @return 返回验证码
     */
    public R<String> code(@RequestBody User user) {

        return R.error("接口未实现");
    }

    /**
     * 用户登录, 这里应该还有验证码, 但是前端没有给传送, 前端也没有请求验证码, 离大谱
     *
     * @param user 用户登录手机号
     * @return 返回登录成功
     */
    @PostMapping("login")
    public R<String> login(@RequestBody User user,
                           HttpServletRequest request) {

        return userService.login(user, request);
    }
}
