package edu.dgut.networkengin2018_2.wechat_mall.service;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;

import java.util.List;

public interface GoodsCategoryService {

    /**
     * 分页
     * @return
     */
    PageResultUtil getCategoriesPage(PageQueryUtil pageQueryUtil);

    /**
     * 添加分类
     * @param  category
     * @return
     */
    String insertCategory(Category category);

    /**
     * 修改分类
     * @param category
     * @return
     */
    String updateGoodsCategory(Category category);

    /**
     * 通过主键获取分类
     * @param id
     * @return
     */
    Category getGoodsCategoryById(Integer id);

    /**
     * 批量删除分类
     * @param ids
     * @return
     */
    Boolean deleteBatch(Integer[] ids);


}
