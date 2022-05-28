package com.huijixu;

import com.huijixu.util.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartSchoolApplicationTests {

    @Test

    public void test_GenerateCheckCode() {
        String encrypt = MD5.encrypt("admin");
        System.out.println(encrypt);
    }


}
