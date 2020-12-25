package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.OrdersgoodsMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Ordersgoods;
import edu.dgut.networkengin2018_2.wechat_mall.service.OrdersGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderGoodsServiceImpl implements OrdersGoodsService {
    @Autowired
    private OrdersgoodsMapper ordersgoodsMapper;


    @Override
    public Boolean createOrderToGoods(List<Ordersgoods> ordersGoodsList) {

        for (int i = 0; i <ordersGoodsList.size(); i++) {
            ordersgoodsMapper.insert(ordersGoodsList.get(i));
        }

        return null;
    }

    @Override
    public List<Ordersgoods> getOrderGoodsById(Integer orderId) {
        return ordersgoodsMapper.getOrdersGoodsById(orderId);
    }
}
