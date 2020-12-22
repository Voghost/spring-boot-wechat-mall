package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Swiperdata;
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
class SwiperdataMapperTest {

    @Resource
    SwiperdataMapper swiperdatamapper;

    @Test
    void getAllList() {
    }

    @Test
    void insert() {
        Swiperdata swiperdata = new Swiperdata();
        swiperdata.setImageSrc("asfasfdsyrtdyg");
        swiperdata.setNavigatorUrl("safasfxzfa");
        int num = swiperdatamapper.insert(swiperdata);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
        int num = swiperdatamapper.deleteByPrimaryKey(1);
        assertEquals(num,1);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findSwiperdataList() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        List<Swiperdata> goodsList = swiperdatamapper.findSwiperdataList(pageQueryUtil);
        assertEquals(goodsList.size(),2);
    }

    @Test
    void updateByPrimaryKey() {
        Swiperdata swiperdata = new Swiperdata();
        swiperdata.setSwiperId(2);
        swiperdata.setImageSrc("修改");
        swiperdata.setOpenType("fqwrqw");
        swiperdata.setGoodsId(4);;
        swiperdata.setNavigatorUrl("dsgsdgdsg");
        int num = swiperdatamapper.updateByPrimaryKey(swiperdata);
        assertEquals(num,1);
    }

    @Test
    void getTotalOrders() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        int num= swiperdatamapper.getTotalSwiperdata();
        assertEquals(num,7);

    }
}