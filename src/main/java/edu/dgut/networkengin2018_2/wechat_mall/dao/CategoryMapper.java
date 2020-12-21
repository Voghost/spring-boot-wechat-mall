package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 得到所有商品
     * @return
     */
    List<Category>  getAllList();

    /**
     * 插入数据 ，并且返回自增的key值
     * @param category
     * @return
     */
    int insert(Category category);

    /**
     * 通过主键删除数据
     * @param catId
     * @return
     */
    int deleteByPrimaryKey(Integer catId);

    /**
     *
     * @param catId
     * @return
     */
    Category selectByPrimaryKey(Integer catId);

    /**
     * 分页查询(包括关键字)
     * @return
     */
    List<Category> findCategoryList(PageQueryUtil pageQueryUtil);


    /**
     * 更新
     */
    int updateByPrimaryKey(Category category);

}
