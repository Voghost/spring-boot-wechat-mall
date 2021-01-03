package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.MyUser;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyUserMapper {

    List<MyUser>  getAllList();

    int insert(MyUser myUser);

    int deleteByPrimaryKey(Integer userId);

    MyUser selectByPrimaryKey(Integer userId);

    List<MyUser> findMyUsersList(PageQueryUtil pageQueryUtil);

    int updateByPrimaryKey(MyUser myUser);

    int getTotalMyUsers();

    int deleteBatch(Integer[] ids);
}
