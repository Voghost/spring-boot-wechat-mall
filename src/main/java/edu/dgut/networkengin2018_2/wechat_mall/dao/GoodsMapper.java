package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    /**
     * 得到所有商品
     * @return
     */
    List<Goods>  getAllList();

    /**
     * 插入数据 ，并且返回自增的key值
     * @param goods
     * @return
     */
    int insert(Goods goods);

    /**
     * 通过主键删除数据
     * @param goodsId
     * @return
     */
    int deleteByPrimaryKey(Integer goodsId);

    /**
     *
     * @param goodsId
     * @return
     */
    Goods selectByPrimaryKey(Integer goodsId);

    /**
     * 分页查询(包括关键字)
     * @return
     */
    List<Goods> findGoodsList(PageQueryUtil pageQueryUtil);


    /**
     * 更新
     */
    int updateByPrimaryKey(Goods goods);

}
