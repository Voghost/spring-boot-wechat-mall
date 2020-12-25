package edu.dgut.networkengin2018_2.wechat_mall;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mysql.cj.conf.PropertyDefinitions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class WechatMallApplicationTests {

    @Test
    void contextLoads() {
    }

}

class MyTest{
    public static void main(String[] args) {
        Date date = new Date();
        Long a = date.getTime()/1000;
        System.out.println(a);

    }
}

