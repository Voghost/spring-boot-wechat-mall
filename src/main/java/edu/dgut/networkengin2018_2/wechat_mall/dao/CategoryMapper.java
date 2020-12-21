package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 得到全部分类数量
     * @return
     */
    int getTotalCategories();

    /**
     * 通过名字和分类深度获取分类
     * 用于判断是否有命名冲突
     * @param categoryLevel
     * @param categoryName
     * @return
     */
    Category selectByLevelAndName(Integer categoryLevel,String categoryName);
}
