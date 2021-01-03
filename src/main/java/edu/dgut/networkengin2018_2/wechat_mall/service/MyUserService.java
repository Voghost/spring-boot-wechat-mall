package edu.dgut.networkengin2018_2.wechat_mall.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MyUserService {
   /**
    * 登录成功
    * @param model
    * @return
    */
   String loginSuccess(Model model);

   String main(Model model);

   String deniedAccess(Model model);

   String logout(HttpServletRequest request, HttpServletResponse response);

}
