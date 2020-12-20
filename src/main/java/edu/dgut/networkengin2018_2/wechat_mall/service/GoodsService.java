package edu.dgut.networkengin2018_2.wechat_mall.service;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;

import java.util.List;

public interface GoodsService {
    /**
     * 获取所有商品
     * @return
     */
    List<Goods> getAllGoods();

    int insertGoods(Goods goods);
}

