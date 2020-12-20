package edu.dgut.networkengin2018_2.wechat_mall.dao;


import edu.dgut.networkengin2018_2.wechat_mall.entity.Goodsattr;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;

import java.util.List;

public interface GoodsAttrMapper {
    List<Goodsattr> getAllList();
    int insert(Goodsattr goodsAttr);
    int deleteByPrimaryKey(Integer attrId);
    Goodsattr selectByPrimaryKey(Integer attrId);
    List<Goodsattr> findUsersList(PageQueryUtil pageQueryUtil);
    int updateByPrimaryKey(Goodsattr goodsAttr);
}
