package com.itheima.reggie;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;


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
}
