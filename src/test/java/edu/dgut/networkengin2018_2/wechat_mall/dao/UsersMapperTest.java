package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Users;
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
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findUsersList() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}