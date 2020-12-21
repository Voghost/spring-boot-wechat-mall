package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
        category.setCatName("asfasf");
        category.setCatPid(1);
        category.setCatLevel(1);
        category.setCatIcon("asfasf");
        int num = categoryMapper.insert(category);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findCategoryList() {
    }

    @Test
    void updateByPrimaryKey() {
    }

    @Test
    void selectByLevelAndName() {
        Integer level =1;
        String catName = "asfasf";
        Category category= categoryMapper.selectByLevelAndName(level,catName);
        assertNotNull(category);
    }
}