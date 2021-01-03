package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminController {
    /**
     * 后台主界面
     *
     * @param request
     * @return
     */
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "admin/index";
    }

    /**
     * 登录界面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "/admin/login";
    }

}
