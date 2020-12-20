package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    @ResponseBody
    @GetMapping("/goods")
    private Map<String,Object> listGoods(){
        Map<String,Object> modelMap = new HashMap<>();
        List<Goods> list= goodsService.getAllGoods();
        modelMap.put("areaList",list);
        return modelMap;
    }
}
