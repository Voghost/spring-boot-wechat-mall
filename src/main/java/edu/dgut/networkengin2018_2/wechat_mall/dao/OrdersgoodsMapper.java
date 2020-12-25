package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Ordersgoods;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersgoodsMapper {

    /**
     * 得到所有商品
     * @return
     */
    List<Ordersgoods>  getAllList();

    /**
     * 插入数据 ，并且返回自增的key值
     * @param ordersgoods
     * @return
     */
    int insert(Ordersgoods ordersgoods);

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
    Ordersgoods selectByPrimaryKey(Integer orderId);

    /**
     * 分页查询(包括关键字)
     * @return
     */
    List<Ordersgoods> findOrdersgoodsList(PageQueryUtil pageQueryUtil);


    /**
     * 更新
     */
    int updateByPrimaryKey(Ordersgoods ordersgoods);

    /**
     * 获取总数
     * @return
     */
    int getTotalOrdersGoods();

    /**
     * 通过id获取商品列表
     * @param orderId
     * @return
     */
    List<Ordersgoods> getOrdersGoodsById(Integer orderId);

}
