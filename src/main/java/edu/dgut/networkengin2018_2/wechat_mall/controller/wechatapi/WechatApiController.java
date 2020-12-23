package edu.dgut.networkengin2018_2.wechat_mall.controller.wechatapi;

import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/wechatapi")
@Controller
public class WechatApiController {

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @GetMapping("/categories")
    @ResponseBody
    public Map<String,Object> test(){
        return goodsCategoryService.getCategoryTree();
    }


}
