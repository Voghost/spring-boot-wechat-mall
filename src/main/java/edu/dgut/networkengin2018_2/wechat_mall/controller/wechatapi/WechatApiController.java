package edu.dgut.networkengin2018_2.wechat_mall.controller.wechatapi;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Swiperdata;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsCategoryService;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.service.SwiperdataService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/wechatapi")
@Controller
public class WechatApiController {

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private SwiperdataService swiperdataService;

    @GetMapping("/categories")
    @ResponseBody
    public Map<String, Object> categories() {
        return goodsCategoryService.getCategoryTree();
    }

    @GetMapping("/goods/detail")
    @ResponseBody
    public Map<String, Object> goodsDetail(@RequestParam("goods_id") Integer goodsId) {
        if (goodsId == null) {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> meta = new HashMap<>();
            meta.put("msg", "商品id不能为空");
            meta.put("status", 400);
            map.put("message", null);
            map.put("meta", meta);
            return map;
        }
        Map<String, Object> goodsMap = goodsService.getGoodsByIdForWechat(goodsId);
        return goodsMap;
    }

    @GetMapping("/goods/search")
    @ResponseBody
    public Map<String, Object> goodsSearch(@RequestParam(value = "query",required = false) String query, /*query 查询商品名字*/
                                           @RequestParam(value = "cid",required = false) Integer cid,
                                           @RequestParam(value = "pagenum",required = false) Integer pageNum,
                                           @RequestParam(value = "pagesize",required = false) Integer pageSize) {
        if(query==null){
            query=null;
        }
        return goodsService.getGoodsPageForWechat(query,cid,pageNum,pageSize);
    }


    /**
     * 用于返回主页轮播图信息
     * @return
     */
    @GetMapping("/home/swiperdata")
    @ResponseBody
    public Map<String,Object> swiper(){
        return  swiperdataService.getAllSwiperDataForWechat();
    }



}
