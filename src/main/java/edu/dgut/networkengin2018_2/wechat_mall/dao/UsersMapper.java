package edu.dgut.networkengin2018_2.wechat_mall.dao;


import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Users;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UsersMapper {
    List<Users> getAllList();

    int insert(Users users);

    int deleteByPrimaryKey(Integer userId);

    Users selectByPrimaryKey(Integer userId);

    List<Users> findUsersList(PageQueryUtil pageQueryUtil);

    int updateByPrimaryKey(Users users);

    int getTotalUser();

    Users getUserByOpenId(String userOpenId);

}
