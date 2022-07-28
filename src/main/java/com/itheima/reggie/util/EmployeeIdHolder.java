package com.itheima.reggie.util;

/**
 * 基于 ThreadLocal 封装, 保存和获取当前登录用户的 id
 * 现在这个比 BaseContext 更有可读性, 但是不通用
 *
 * @author HeYunjia
 */
public class EmployeeIdHolder {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void set(Long id) {
        threadLocal.set(id);
    }

    public static Long get() {
        return threadLocal.get();
    }
}
