package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.security.core.context.SecurityContextHolder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class MyUserServiceImpl implements MyUserService {


    /**
     * 登录成功
     * @param model
     * @return
     */
    @Override
    public String loginSuccess(Model model) {
        model.addAttribute("user",getUserName());
        model.addAttribute("role",getAuthority());
        return "/admin";
    }

    @Override
    public String main(Model model) {
        return null;
    }

    @Override
    public String deniedAccess(Model model) {
        model.addAttribute("user",getUserName());
        model.addAttribute("role",getAuthority());
        return "/deniedAccess";
    }

    @Override
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/login?logout";
    }




    /**
     * 获取当前用户名称
     *
     * @return
     */
    private String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * 获取当前用户权限
     * @return
     */
    private String getAuthority() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String>  roles = new ArrayList<String>();
        for(GrantedAuthority grantedAuthority: authentication.getAuthorities()){
            roles.add(grantedAuthority.getAuthority());
        }
        return roles.toString();
    }


}
