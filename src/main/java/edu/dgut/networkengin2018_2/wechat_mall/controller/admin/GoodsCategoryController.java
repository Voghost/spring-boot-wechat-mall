package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class GoodsCategoryController {

    @Resource
    private GoodsCategoryService goodsCategoryService;

    public String categoriesPage(
            HttpServletRequest request,
            @RequestParam("categoryLevel") Integer catLevel,
            @RequestParam("parentId") Integer parentId,


            ){

    }


}
