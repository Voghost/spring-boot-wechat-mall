package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * 通过id获取商品具体信息
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
     * 查询到商品的总数
     * @param pageQueryUtil
     * @return
     */
    int getTotalGoods(PageQueryUtil pageQueryUtil);


    /**
     * 更新
     */
    int updateByPrimaryKey(Goods goods);

    /**
     * 批量修改上下架
     * @param orderIds
     * @param sellStatus
     * @return
     */
    int batchUpdateSellStatus(@Param("goodsIds")Integer[] orderIds, @Param("sellStatus") int sellStatus);

    /**
     * 用于微信api的页查询
     * @return
     */
    List<Goods> getGoodsPageForWechat(Map<String,Object> map);

}
