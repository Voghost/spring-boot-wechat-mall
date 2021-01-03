package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.CategoryMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsCategoryService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResultUtil getCategoriesPage(PageQueryUtil pageQueryUtil) {
        List<Category> categories = categoryMapper.findCategoryList(pageQueryUtil);
        int total = categoryMapper.getTotalCategories();

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
        if (ids.length < 1) {
            return false;
        }
        //删除分类数据
        return categoryMapper.deleteBatch(ids) > 0;
    }

    @Override
    public List<Category> selectByLevelAndParentIdsAndNumber(List<Integer> parentIds, int categoryLevel) {
        return categoryMapper.selectByLevelAndParentIdsAndNumber(parentIds, categoryLevel);
    }

    @Override
    public Map<String, Object> getCategoryTree() {

        List<Map<String, Object>> mapList1 = new ArrayList<>();


        List<Category> firstCategories = categoryMapper.selectByLevel(1);

        //第一层
        for (int i = 0; i < firstCategories.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("cat_id", firstCategories.get(i).getCatId());
            map.put("cat_name", firstCategories.get(i).getCatName());
            map.put("cat_pid", firstCategories.get(i).getCatPid());
            map.put("cat_level", firstCategories.get(i).getCatLevel());
            map.put("cat_deleted", firstCategories.get(i).getCatDeleted());
            map.put("cat_icon", firstCategories.get(i).getCatIcon());

            //第二层
            List<Map<String, Object>> mapList2 = new ArrayList<>();
            List<Category> secondCategories = categoryMapper
                    .selectByLevelAndParentIdsAndNumber(Collections
                            .singletonList(firstCategories.get(i).getCatId()), 2);
            for (int n = 0; n < secondCategories.size(); n++) {
                Map<String, Object> map2 = new HashMap<>();
                map2.put("cat_id", secondCategories.get(n).getCatId());
                map2.put("cat_name", secondCategories.get(n).getCatName());
                map2.put("cat_pid", secondCategories.get(n).getCatPid());
                map2.put("cat_level", secondCategories.get(n).getCatLevel());
                map2.put("cat_deleted", secondCategories.get(n).getCatDeleted());
                map2.put("cat_icon", secondCategories.get(n).getCatIcon());

                //第三层
                List<Map<String, Object>> mapList3 = new ArrayList<>();
                List<Category> thirdCategories = categoryMapper
                        .selectByLevelAndParentIdsAndNumber(Collections
                                .singletonList(secondCategories.get(n).getCatId()), 3);
                for (int m = 0; m <thirdCategories.size(); m++) {
                    Map<String, Object> map3 = new HashMap<>();
                    map3.put("cat_id",thirdCategories.get(m).getCatId());
                    map3.put("cat_name",thirdCategories.get(m).getCatName());
                    map3.put("cat_pid",thirdCategories.get(m).getCatPid());
                    map3.put("cat_level",thirdCategories.get(m).getCatLevel());
                    map3.put("cat_deleted",thirdCategories.get(m).getCatDeleted());
                    map3.put("cat_icon",thirdCategories.get(m).getCatIcon());
                    mapList3.add(map3);
                }
                map2.put("children",mapList3);
                mapList2.add(map2);

            }
            map.put("children",mapList2) ;
            mapList1.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("message", mapList1);
        return result;
    }
}
