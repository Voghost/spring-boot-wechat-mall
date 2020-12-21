package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Goodsattr;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int num = goodsAttrMapper.deleteByPrimaryKey(3);
        assertEquals(1,num);
    }

    @Test
    void selectByPrimaryKey() {
        Goodsattr num = goodsAttrMapper.selectByPrimaryKey(4);
        assertNotNull(num);
    }

    @Test
    void findUsersList() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        List<Goodsattr> goodsattrList = goodsAttrMapper.findUsersList(pageQueryUtil);
        assertEquals(goodsattrList.size(),3);
    }

    @Test
    void updateByPrimaryKey() {
        Goodsattr goodsattr = new Goodsattr();
        goodsattr.setAttrId(4);
        goodsattr.setGoodsId(1);
        goodsattr.setAttrValue("更改222");
        goodsattr.setAttrPrice(22.33);
        goodsattr.setAttrName("更改");
        goodsattr.setAttrSel("sfaas");
        goodsattr.setAttrWrite("fasfasc");
        int num =goodsAttrMapper.updateByPrimaryKey(goodsattr);
        assertEquals(num,1);
    }

    @Test
    void getTotalGoodsAttr() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        int num= goodsAttrMapper.getTotalGoodsAttr();
        assertEquals(num,1);

    }
}