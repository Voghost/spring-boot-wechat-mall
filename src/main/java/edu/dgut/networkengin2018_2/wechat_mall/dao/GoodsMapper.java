package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    List<Goods>  getAllGoodsList();

    // 通过主键删除
//    int deleteByPrimaryKey(Integer goodsId);

    // 添加商品
//    int insert(Goods goods);
}
