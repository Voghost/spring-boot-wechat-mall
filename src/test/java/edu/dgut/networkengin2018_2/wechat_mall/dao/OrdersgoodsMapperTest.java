package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Ordersgoods;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrdersgoodsMapperTest {

    @Resource
    OrdersgoodsMapper ordersgoodsMapper;

    @Test
    void getAllList() {
    }

    @Test
    void insert() {
        Ordersgoods ordersgoods = new Ordersgoods();
        ordersgoods.setOrderId(1);
        ordersgoods.setOrderGoodsId(1);
        ordersgoods.setOrderGoodsNumber("afsasf");
        ordersgoods.setOrderPrice(2345.123);
        int num = ordersgoodsMapper.insert(ordersgoods);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findOrdersgoodsList() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}