package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Swiperdata;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SwiperdataMapper {

    /**
     * 得到所有商品
     * @return
     */
    List<Swiperdata>  getAllList();

    /**
     * 插入数据 ，并且返回自增的key值
     * @param swiperdata
     * @return
     */
    int insert(Swiperdata swiperdata);

    /**
     * 通过主键删除数据
     * @param swiperId
     * @return
     */
    int deleteByPrimaryKey(Integer swiperId);

    /**
     *
     * @param swiperId
     * @return
     */
    Swiperdata selectByPrimaryKey(Integer swiperId);

    /**
     * 分页查询(包括关键字)
     * @return
     */
    List<Swiperdata> findSwiperdataList(PageQueryUtil pageQueryUtil);


    /**
     * 更新
     */
    int updateByPrimaryKey(Swiperdata swiperdata);

    int getTotalSwiperdata();
}
