package edu.dgut.networkengin2018_2.wechat_mall.service;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;

import java.util.List;

public interface GoodsService {
    /**
     * 获取所有商品
     * @return
     */
    List<Goods> getAllGoods();

    /**
     * 插入商品
     * @param goods
     * @return
     */
    String insertGoods(Goods goods);


    /**
     * 分页查询
     * @param pageUtil 传入分页查询请求 (map)
     * @return 通过map包装后的结果集
     */
    PageResultUtil getGoodsPage(PageQueryUtil pageUtil);



    /**
     * 修改商品信息
     * @param goods
     * @return
     */
    String updateGoods(Goods goods);


    /**
     * 通过主键获取商品详情
     * @param id
     * @return
     */
    Goods getGoodsById(Integer id);




}

