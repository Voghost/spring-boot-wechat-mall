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
        Users users = new Users();
        users.setUserOpenId("test");
        users.setUserToken("888");
        users.setUserCreateTime(new Date());
        users.setUserUpdateTime(new Date());
        users.setUserLastLoginTime(new Date());
        int num = usersMapper.insert(users);
        assertEquals(num, 1);
    }

    @Test
    void deleteByPrimaryKey() {
        int num = usersMapper.deleteByPrimaryKey(5);
        assertEquals(1, num);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findUsersList() {
        Map<String, Object> list = new HashMap<>();
        list.put("page", 2);
        list.put("limit", 3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        List<Users> goodsList = usersMapper.findUsersList(pageQueryUtil);
        assertEquals(goodsList.size(), 1);
    }

    @Test
    void updateByPrimaryKey() {
        Users users = new Users();
        users.setUserId(4);
        users.setUserToken("ttt");
        users.setUserOpenId("openid");
        int num = usersMapper.updateByPrimaryKey(users);
        assertEquals(num, 1);
    }

    @Test
    void getTotalUsers() {
        Map<String, Object> list = new HashMap<>();
        list.put("page", 2);
        list.put("limit", 3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        int num = usersMapper.getTotalUser();
        assertEquals(num, 0);

    }

    @Test
    void getUserByUserInfo() {
        String userOpenId = "test";
        Users user = usersMapper.getUserByOpenId(userOpenId);
        assertNotNull(user);
    }
}