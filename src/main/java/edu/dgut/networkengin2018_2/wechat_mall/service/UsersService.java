package edu.dgut.networkengin2018_2.wechat_mall.service;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Users;
import edu.dgut.networkengin2018_2.wechat_mall.entity.WechatLogin;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;

import java.util.Map;

public interface UsersService {

     PageResultUtil getUsersPage(PageQueryUtil pageQueryUtil);

     Map<String,Object> wechatLogin(WechatLogin wechatLogin);

     Users getUserByToken(String token);

     Boolean lockUsers(Integer[] ids, int lockStatus);

     int getCountOfUser();



}
