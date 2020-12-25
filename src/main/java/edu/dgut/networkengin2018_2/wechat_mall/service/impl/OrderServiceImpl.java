package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.OrdersMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Orders;
import edu.dgut.networkengin2018_2.wechat_mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public Map<String, Object> createOrder(Orders orders) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100));
        String orderNumber =  "ORDER"+ tempName;

        orders.setOrderNumber(orderNumber);
        //设置时间
        orders.setOrderCreateTime(new Date());
        orders.setOrderUpdateTime(new Date());
        orders.setOrderState(0);

        ordersMapper.insert(orders);

        Map<String,Object> result = new HashMap<>();
        result.put("order_id",orders.getOrderId());
        result.put("user_id",orders.getOrderUserId());
        result.put("order_number",orders.getOrderNumber());
        result.put("order_price",orders.getOrderPrice());
        result.put("create_time",orders.getOrderCreateTime().getTime()/1000); //将时间转化为时间戳
        result.put("update_time",orders.getOrderUpdateTime().getTime()/1000); //将时间转化为时间戳

        return result;
    }

    @Override
    public List<Orders> getOrderByUserId(Integer userId,Integer orderStatus) {
        return ordersMapper.getOrderByUserIdAndOrderStatus(userId, orderStatus);
    }

    @Override
    public Orders getOrderByNumber(String orderNum) {
        return ordersMapper.getOrderByNumber(orderNum);
    }

    @Override
    public int updateOrderStatusByNumber(String orderNumber, Integer orderStatus) {
       return ordersMapper.updateOrderStatusByNumber(orderNumber,orderStatus);
    }


}
