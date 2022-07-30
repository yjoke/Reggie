package com.itheima.reggie;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.Arrays;


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
}
