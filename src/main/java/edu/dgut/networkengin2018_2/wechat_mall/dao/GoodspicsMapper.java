package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goodspics;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodspicsMapper {

    /**
     * 得到所有商品
     * @return
     */
    List<Goodspics>  getAllList();

    /**
     * 插入数据 ，并且返回自增的key值
     * @param goodspics
     * @return
     */
    int insert(Goodspics goodspics);

    /**
     * 通过主键删除数据
     * @param picsId
     * @return
     */
    int deleteByPrimaryKey(Integer picsId);

    /**
     *
     * @param picsId
     * @return
     */
    Goodspics selectByPrimaryKey(Integer picsId);

    /**
     * 分页查询(包括关键字)
     * @return
     */
    List<Goodspics> findGoodspicsList(PageQueryUtil pageQueryUtil);


    /**
     * 更新
     */
    int updateByPrimaryKey(Goodspics goodspics);

    int getTotalGoodspics();
}
