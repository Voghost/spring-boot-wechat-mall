package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goodsattr;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class GoodsAttrMapperTest {
    @Resource
  GoodsAttrMapper goodsAttrMapper;
    @Test
    void getAllList() {
    }

    @Test
    void insert() {
        Goodsattr goodsattr=new Goodsattr();
        goodsattr.setAttrValue("114");
        goodsattr.setAttrPrice(22.22);
        goodsattr.setAttrName("abc");
        goodsattr.setAttrSel("3378");
        goodsattr.setAttrWrite("5678");
        int num =goodsAttrMapper.insert(goodsattr);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findUsersList() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}