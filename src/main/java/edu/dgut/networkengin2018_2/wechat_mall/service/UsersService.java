package edu.dgut.networkengin2018_2.wechat_mall.service;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Users;
import edu.dgut.networkengin2018_2.wechat_mall.entity.WechatLogin;

import java.util.Map;

public interface UsersService {

     Map<String,Object> wechatLogin(WechatLogin wechatLogin);

     Users getUserByToken(String token);

}
