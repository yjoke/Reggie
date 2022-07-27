package com.itheima.reggie.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HeYunjia
 */

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @PostMapping("login")
    public String login() {
        return null;
    }

}
