package com.itheima.reggie;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Set;


/**
 * @author HeYunjia
 */

@SpringBootTest
public class TestMain {

    @Test
    void testMD5() {
        String bytes = DigestUtil.md5Hex("123456".getBytes());
        String s = DigestUtils.md5DigestAsHex("123456".getBytes());

        System.out.println(bytes);
        System.out.println(s);

    }

    @Test
    void testSubstring() {
        String src = "Duplicate entry '粤菜' for key 'idx_category_name'";
        int i1 = src.indexOf('\'', 17);
        System.out.println(src.substring(17, i1));

    }

    @Test
    void testSplit() {
        String ids = "1397849739276890114,1397850140982161409,1397850392090947585,1397850851245600769,1397851099502260226,1397851370462687234,1397851668262465537,1397852391150759938,1397853183287013378,1397853709101740034";
        String[] id = ids.split(",");
        System.out.println(Arrays.toString(id));
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testRedis() {
        stringRedisTemplate.opsForValue()
                .set("test", "testValue");

        String test = stringRedisTemplate.opsForValue()
                .get("test");

        System.out.println(test);
    }

    @Test
    void testPage() {
        Page<EmployeeDTO> pageInfo = new Page<>();
        // 查找 id 集合 key, 查看 是否存在, 存在的话返回前前十个 id
        Set<String> range = stringRedisTemplate.opsForZSet().range("", 0, 9);

        // 存在, , 拼接 Page<> 返回信息
        // if (range != null) 传入id集合, 返回数据,

        // 不存在, 查询数据库所有 id 数据 保存在 redis

        // 存在了 id 数据, 递归返回.

        // // 子函数, 按照 id 去查询 redis, 如果不存在, 就去查找数据库.
    }
}
