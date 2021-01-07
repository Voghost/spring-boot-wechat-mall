package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.OrdersMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Orders;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Ordersgoods;
import edu.dgut.networkengin2018_2.wechat_mall.service.OrderService;
import edu.dgut.networkengin2018_2.wechat_mall.service.OrdersGoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersGoodsService ordersGoodsService;

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

    @Override
    public PageResultUtil getOrderPage(PageQueryUtil pageQueryUtil) {
        List<Orders> orders = ordersMapper.findOrdersList(pageQueryUtil);
        int total =ordersMapper.getTotalOrders(pageQueryUtil);
        PageResultUtil pageResult = new PageResultUtil(orders, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public String updateOrderInfo(Orders orders) {
        Orders temp = ordersMapper.selectByPrimaryKey(orders.getOrderId());
        if(temp!=null && temp.getOrderState() >=0){
            temp.setOrderPrice(orders.getOrderPrice());
            temp.setOrderAddress(orders.getOrderAddress());
            temp.setOrderUpdateTime(orders.getOrderUpdateTime());
            if(ordersMapper.updateByPrimaryKey(temp)>0){
                return "修改成功";
            }
            return  "修改失败";
        }
        return "数据不存在";
    }

    /**
     * 发货
     * @param ids
     * @return
     */
    @Override
    public String checkOut(Integer[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<Orders> orders = ordersMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Orders  order: orders) {
                if (order.getOrderState() != 1) {
                    errorOrderNos += order.getOrderNumber() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行配货完成操作 修改订单状态和更新时间
                if (ordersMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return "修改成功";
                } else {
                    return "修改失败";
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功的订单，无法执行配货完成操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return "查询不到数据";
    }

    @Override
    public String closeOrder(Integer[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<Orders> orders = ordersMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Orders order: orders) {
                // isDeleted=1 一定为已关闭订单
                //已关闭或者已完成无法关闭订单
                if (order.getOrderState() == 3 || order.getOrderState() < 0) {
                    errorOrderNos += order.getOrderNumber() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行关闭操作 修改订单状态和更新时间
                if (ordersMapper.closeDone(Arrays.asList(ids)) > 0) {
                    return "修改成功";
                } else {
                    return "修改失败";
                }
            } else {
                //订单此时不可执行关闭操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单不能执行关闭操作";
                } else {
                    return "你选择的订单不能执行关闭操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return "找不到数据";
    }

    @Override
    public List<Ordersgoods> getOrderGoods(Integer id) {
        Orders orders = ordersMapper.selectByPrimaryKey(id);
        if(orders !=null){
            List<Ordersgoods> ordersgoodsList = ordersGoodsService.getOrderGoodsById(orders.getOrderId());
            return ordersgoodsList;
        }
        return null;
    }

    @Override
    public List<Orders> getAllOrdersForExcel() {
        return ordersMapper.getAllList();
    }


}
