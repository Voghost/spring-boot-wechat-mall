package edu.dgut.networkengin2018_2.wechat_mall.service;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Ordersgoods;

import java.util.List;
import java.util.Map;

public interface OrdersGoodsService {

    /**
     * 插入商品
     *
     * @param ordersGoodsList
     * @return
     */
    public Boolean createOrderToGoods(List<Ordersgoods> ordersGoodsList);

    public List<Ordersgoods> getOrderGoodsById(Integer orderId);


}
