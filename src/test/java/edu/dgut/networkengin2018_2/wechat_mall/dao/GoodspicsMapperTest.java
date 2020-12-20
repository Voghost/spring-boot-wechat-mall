package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goodspics;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class GoodspicsMapperTest {

    @Resource
    GoodspicsMapper goodspicsMapper;

    @Test
    void getAllList() {
    }

    @Test
    void insert() {
        Goodspics goodspics = new Goodspics();
        goodspics.setGoodsId(1);
        goodspics.setPicsBig("asfasf");
        goodspics.setPicsMid("afsfdasd");
        goodspics.setPicsSma("afsasdf");
        int num = goodspicsMapper.insert(goodspics);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findGoodspicsList() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}