package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryMapperTest {

    @Resource
    CategoryMapper categoryMapper;

    @Test
    void getAllList() {
    }

    @Test
    void insert() {
        Category category = new Category();
        category.setCatName("yutuy");
        category.setCatPid(20);
        category.setCatLevel(18);
        category.setCatDeleted(false);
        category.setCatIcon("jiuh");
        int num = categoryMapper.insert(category);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
        int num = categoryMapper.deleteByPrimaryKey(2);
        assertEquals(3,num);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findCategoryList() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        List<Category> goodsList = categoryMapper.findCategoryList(pageQueryUtil);
        assertEquals(goodsList.size(),2);
    }

    @Test
    void updateByPrimaryKey() {
        Category category = new Category();
        category.setCatId(18);
        category.setCatName("yutuy");
        category.setCatPid(20);
        category.setCatLevel(18);
        category.setCatDeleted(false);
        category.setCatIcon("asfasd");
        int num = categoryMapper.updateByPrimaryKey(category);
        assertEquals(num,1);
    }

    @Test
    void getTotalCategory() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        int num= categoryMapper.getTotalCategories();
        assertEquals(num,7);

    }

    @Test
    void selectByLevelAndName() {
        Integer level =1;
        String catName = "asfasf";
        Category category= categoryMapper.selectByLevelAndName(level,catName);
        assertNotNull(category);
    }
}