package com.itheima.reggie.controller;

import com.itheima.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */

@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;
}
