package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.CategoryMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsCategoryService;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResultUtil getCategoriesPage(PageQueryUtil pageQueryUtil) {
        List<Category> categories = categoryMapper.findCategoryList(pageQueryUtil);
        int total = categoryMapper.getTotalCateGories();

        // 将结果集存入包装类
        PageResultUtil pageResultUtil = new PageResultUtil(categories, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResultUtil;
    }

    @Override
    public String insertCategory(Category category) {
        Category tmp = categoryMapper.selectByLevelAndName(category.getCatLevel(), category.getCatName());
        if (tmp != null) {
            return "该数据已存在";
        }
        if (categoryMapper.insert(category) > 0) {
            return "插入成功";
        }
        return "错误";
    }

    @Override
    public String updateGoodsCategory(Category category) {
        Category temp = categoryMapper.selectByPrimaryKey(category.getCatId());
        // 如果为空  表示不能修改
        if (temp == null) {
            return "没有该条数据";
        }

        // 判断是否有同名但id不同
        Category temp2 = categoryMapper.selectByLevelAndName(category.getCatLevel(), category.getCatName());
        if (temp2 != null && !(temp2.getCatId().equals(category.getCatId()))) {
            //同名且不同id 不能继续修改
            return "名字冲突";
        }

        if (categoryMapper.updateByPrimaryKey(category) > 0) {
            return "成功";
        }
        return "错误";
    }

    @Override
    public Category getGoodsCategoryById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return null;
    }
}
