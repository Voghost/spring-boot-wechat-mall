package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Orders;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersMapper {

    /**
     * 得到所有商品
     * @return
     */
    List<Orders>  getAllList();

    /**
     * 插入数据 ，并且返回自增的key值
     * @param orders
     * @return
     */
    int insert(Orders orders);

    /**
     * 通过主键删除数据
     * @param orderId
     * @return
     */
    int deleteByPrimaryKey(Integer orderId);

    /**
     *
     * @param orderId
     * @return
     */
    Orders selectByPrimaryKey(Integer orderId);

    /**
     * 分页查询(包括关键字)
     * @return
     */
    List<Orders> findOrdersList(PageQueryUtil pageQueryUtil);


    /**
     * 更新
     */
    int updateByPrimaryKey(Orders orders);

    int getTotalOrders();

    /**
     * 寻找用户id和订单状态的orders
     * @param userId
     * @return
     */
    List<Orders> getOrderByUserIdAndOrderStatus(Integer userId,Integer orderStatus);

    Orders getOrderByNumber(String orderNumber);

    int updateOrderStatusByNumber(String orderNumber, Integer status);
}
