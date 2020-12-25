package edu.dgut.networkengin2018_2.wechat_mall.service;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Orders;

import java.util.List;
import java.util.Map;

public interface OrderService {
    /**
     * 创建订单(纯订单)并返回订单值
     * @param orders
     * @return
     */
    Map<String,Object>  createOrder(Orders orders);

    List<Orders> getOrderByUserId(Integer userId,Integer orderStatus);

    Orders getOrderByNumber(String orderNum);

    int updateOrderStatusByNumber(String orderNumber, Integer orderStatus);
}
