package edu.dgut.networkengin2018_2.wechat_mall.service;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Orders;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Ordersgoods;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;

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

    /**
     * 更新订单
     * @param orderNumber
     * @param orderStatus
     * @return
     */
    int updateOrderStatusByNumber(String orderNumber, Integer orderStatus);

    /**
     * 订单分页查询
     * @param pageQueryUtil
     * @return
     */
    PageResultUtil getOrderPage(PageQueryUtil pageQueryUtil);

    /**
     * 修改订单
     * @param orders
     * @return
     */
    String updateOrderInfo(Orders orders);

    /**
     * 配货
     * @param ids
     * @return
     */
    String checkOut(Integer[] ids);


    /**
     * 关闭订单
     * @param ids
     * @return
     */
    String closeOrder(Integer[] ids);


    List<Ordersgoods> getOrderGoods(Integer id);








}
