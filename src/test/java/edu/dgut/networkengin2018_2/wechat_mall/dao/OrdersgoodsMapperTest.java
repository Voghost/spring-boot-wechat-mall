package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Ordersgoods;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int num = ordersgoodsMapper.deleteByPrimaryKey(1);
        assertEquals(num,1);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findOrdersgoodsList() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        List<Ordersgoods> goodsList = ordersgoodsMapper.findOrdersgoodsList(pageQueryUtil);
        assertEquals(goodsList.size(),1);
    }

    @Test
    void updateByPrimaryKey() {
        Ordersgoods ordersgoods = new Ordersgoods();
        ordersgoods.setOrderId(1);
        ordersgoods.setOrderGoodsId(3);
        ordersgoods.setOrderPrice(6235.32);
        ordersgoods.setOrderGoodsNumber("fgasf");
        int num = ordersgoodsMapper.updateByPrimaryKey(ordersgoods);
        assertEquals(num,1);
    }
}