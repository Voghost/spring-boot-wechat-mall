package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Goodspics;
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
        int num = goodspicsMapper.deleteByPrimaryKey(1);
        assertEquals(1,num);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findGoodspicsList() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        List<Goodspics> goodsList = goodspicsMapper.findGoodspicsList(pageQueryUtil);
        assertEquals(goodsList.size(),2);
    }

    @Test
    void updateByPrimaryKey() {
        Goodspics goodspics = new Goodspics();
        goodspics.setPicsId(2);
        goodspics.setGoodsId(2);
        goodspics.setPicsBig("fasf");
        goodspics.setPicsMid("gasf");
        goodspics.setPicsSma("fasd");
        int num = goodspicsMapper.updateByPrimaryKey(goodspics);
        assertEquals(num,1);
    }

    @Test
    void getTotalGoodspics() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        int num= goodspicsMapper.getTotalGoodspics();
        assertEquals(num,7);

    }
}