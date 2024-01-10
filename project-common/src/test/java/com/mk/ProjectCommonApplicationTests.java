package com.mk;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.util.function.Consumer;

@SpringBootTest
class ProjectCommonApplicationTests {

    @Test
    void contextLoads() {
        String t="0.00";
        int i = (int) Double.parseDouble(t);
        System.out.println(i);
    }
    @Test
    void tt(){
        String str = "200.1100000000";
        double number = Double.parseDouble(str);
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(number);
        System.out.println(formatted);  // 输出 "200.00"


    }
}
