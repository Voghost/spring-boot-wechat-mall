package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Swiperdata;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SwiperdataMapperTest {

    @Resource
    SwiperdataMapper swiperdatamapper;

    @Test
    void getAllList() {
    }

    @Test
    void insert() {
        Swiperdata swiperdata = new Swiperdata();
        swiperdata.setImageSrc("asfasfdsfg");
        swiperdata.setNavigatorUrl("safasfxzfa");
        int num = swiperdatamapper.insert(swiperdata);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findSwiperdataList() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}