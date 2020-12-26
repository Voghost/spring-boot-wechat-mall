package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class GoodsMapperTest {

    @Resource
    GoodsMapper goodsMapper;

    @Test
    void getAllList() {
    }

    @Test
    void insert() {
        Goods goods = new Goods();
        goods.setGoodsName("test");
        goods.setGoodsPrice(22.22);
        goods.setGoodsWeight(22.22);
        goods.setGoodsAddTime(new Date());
        goods.setGoodsDeleteTime(new Date());
        goods.setGoodsUpdateTime(new Date());
        goods.setGoodsIntroduce("商品介绍");
        goods.setGoodsBigLogo("url");
        goods.setGoodsSmallLogo("url");
        goods.setGoodsIsPromote(false);
        goods.setGoodsPicOne("url");
        goods.setGoodsPicTwo("url");
        goods.setGoodsPicThree("url");
        int num =goodsMapper.insert(goods);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
        int num = goodsMapper.deleteByPrimaryKey(3);
        assertEquals(0,num);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findGoodsList() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        List<Goods> goodsList = goodsMapper.findGoodsList(pageQueryUtil);
        assertEquals(goodsList.size(),3);

    }

    @Test
    void updateByPrimaryKey() {
        Goods goods = new Goods();
        goods.setGoodsId(1);
        goods.setGoodsName("更改222");
        goods.setGoodsPrice(22.22);
        goods.setGoodsWeight(22.22);
        goods.setGoodsAddTime(new Date());
        goods.setGoodsDeleteTime(new Date());
        goods.setGoodsUpdateTime(new Date());
        goods.setGoodsIntroduce("商品介绍");
        goods.setGoodsBigLogo("url");
        goods.setGoodsSmallLogo("url");
        goods.setGoodsIsPromote(false);
        int num =goodsMapper.updateByPrimaryKey(goods);
        assertEquals(num,1);
    }

    @Test
    void getTotalGoods() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        int num= goodsMapper.getTotalGoods(pageQueryUtil);
        assertEquals(num,7);

    }
}