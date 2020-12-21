package edu.dgut.networkengin2018_2.wechat_mall.dao;


import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Users;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;

import java.util.List;

public interface UsersMapper {
    List<Users> getAllList();
    int insert(Users users);
    int deleteByPrimaryKey(Integer usersId);
    Users selectByPrimaryKey(Integer usersId);
    List<Users> findUsersList(PageQueryUtil pageQueryUtil);
    int updateByPrimaryKey(Users users);
}