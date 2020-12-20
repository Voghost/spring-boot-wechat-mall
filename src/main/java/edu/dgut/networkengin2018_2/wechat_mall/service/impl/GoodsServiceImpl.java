package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.GoodsMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;


    /**
     * 得到所有的商品
     * @return
     */
    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.getAllList();
    }

    @Override
    public int insertGoods(Goods goods) {
        return goodsMapper.insert(goods);
    }
}
