package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Users;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class UsersMapperTest {
    @Resource
    UsersMapper usersMapper;
    @Test
    void getAllList() {
    }

    @Test
    void insert() {
        Users users=new Users();
        users.setUserName("yunchan");
        users.setUserId(345);
        users.setUserQQ("41888438");
        users.setUserTel("123456");
        users.setUserHobby("111");
        users.setUserSex("1");
        users.setUserXueli("IBA");
        users.setUserIntroduce("hahaha");

        users.setUserToken("888");
        int num =usersMapper.insert(users);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
        int num = usersMapper.deleteByPrimaryKey(3);
        assertEquals(1,num);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findUsersList() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        List<Users> goodsList = usersMapper.findUsersList(pageQueryUtil);
        assertEquals(goodsList.size(),1);
    }

    @Test
    void updateByPrimaryKey() {
        Users users = new Users();
        users.setUserId(1);
        users.setUserIntroduce("修改");
        int num = usersMapper.updateByPrimaryKey(users);
        assertEquals(num,1);
    }

    @Test
    void getTotalUsers() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        int num= usersMapper.getTotalUser();
        assertEquals(num,7);

    }
}