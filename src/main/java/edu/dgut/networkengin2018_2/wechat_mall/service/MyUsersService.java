package edu.dgut.networkengin2018_2.wechat_mall.service;

import edu.dgut.networkengin2018_2.wechat_mall.entity.MyUser;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;

import java.util.Map;

public interface MyUsersService {

    PageResultUtil getMyUsersPage(PageQueryUtil pageQueryUtil);

    String insertMyUsers(MyUser myUser);

    String updateMyUsers(MyUser myUser);

    MyUser getMyUsersById(Integer id);

    Boolean deleteBatch(Integer[] ids);

    Map<String,Object> getAllMyUsersForWechat();
}
