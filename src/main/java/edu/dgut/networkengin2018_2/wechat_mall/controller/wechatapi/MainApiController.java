package edu.dgut.networkengin2018_2.wechat_mall.controller.wechatapi;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Floor;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Swiperdata;
import edu.dgut.networkengin2018_2.wechat_mall.service.FloorService;
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
import javax.print.attribute.standard.PresentationDirection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/wechatapi")
@Controller
public class MainApiController {

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private SwiperdataService swiperdataService;

    @Resource
    private FloorService floorService;

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
    public Map<String, Object> goodsSearch(@RequestParam(value = "query", required = false) String query, /*query 查询商品名字*/
                                           @RequestParam(value = "cid", required = false) Integer cid,
                                           @RequestParam(value = "pagenum", required = false) Integer pageNum,
                                           @RequestParam(value = "pagesize", required = false) Integer pageSize) {
        if (query == null) {
            query = null;
        }
        return goodsService.getGoodsPageForWechat(query, cid, pageNum, pageSize);
    }

    @GetMapping("goods/qsearch")
    @ResponseBody
    public Map<String, Object> goodsQSerach(@RequestParam(value = "query") String query) {
        return goodsService.getGoodsForWechat(query);
    }

    /**
     * 用于返回主页轮播图信息
     *
     * @return
     */
    @GetMapping("/home/swiperdata")
    @ResponseBody
    public Map<String, Object> swiper() {
        return swiperdataService.getAllSwiperDataForWechat();
    }


    /**
     * 返回主界面导航
     */
    @GetMapping("/home/catitems")
    @ResponseBody
    public Map<String, Object> catimes() {
        List<Map<String, Object>> messageList = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, Object> map4 = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();

        Map<String, Object> result = new HashMap<>();


        map1.put("name", "分类");
        map1.put("image_src", "https://oss.ghovos.top/wechat-mini/public/icon_index_nav_4%402x.png");
        map1.put("open_type", "switchTab");
        map1.put("navigator_url", "/pages/category/index");

        map2.put("name", "秒拍杀");
        map2.put("image_src", "https://oss.ghovos.top/wechat-mini/public/icon_index_nav_3%402x.png");

        map3.put("name", "");
        map3.put("image_src", "https://oss.ghovos.top/wechat-mini/public/icon_index_nav_2%402x.png");

        map4.put("name", "秒拍杀");
        map4.put("image_src", "https://oss.ghovos.top/wechat-mini/public/icon_index_nav_1%402x.png");

        messageList.add(map1);
        messageList.add(map2);
        messageList.add(map3);
        messageList.add(map4);

        meta.put("msg", "获取成功");
        meta.put("status", 200);

        result.put("message", messageList);
        result.put("meta", meta);

        return result;
    }


    /**
     * 返回商品楼层
     */

    @GetMapping("/home/floordata")
    @ResponseBody
    public Map<String, Object> floordata() {
        List<Floor> floorList = floorService.getAllFloorForWechat();
        if (floorList == null) {
            Map<String, Object> meta = new HashMap<>();
            meta.put("msg", "获取失败");
            meta.put("status", "400");

            Map<String, Object> result = new HashMap<>();
            result.put("meta", meta);
            result.put("message", null);
            return result;
        }

        List<Map<String, Object>> message = new ArrayList<>();

        for (int i = 0; i < floorList.size(); i++) {
            Map<String, Object> floor_title = new HashMap<>();
            Map<String, Object> temp = new HashMap<>();
            temp.put("name", floorList.get(i).getFloorName());
            temp.put("image_src", floorList.get(i).getFloorTitleImage());
            temp.put("open_type", "navigate");
            temp.put("navigator_url", "/pages/goods_list?query=" + floorList.get(i).getFloorKeyword());
            floor_title.put("floor_title", temp);
            message.add(floor_title);
        }
        Map<String, Object> meta = new HashMap<>();
        meta.put("msg", "获取成功");
        meta.put("status", "200");

        Map<String, Object> result = new HashMap<>();
        result.put("meta", meta);
        result.put("message", message);

        return result;
    }


/*
    @GetMapping("/home/floordata")
    @ResponseBody
    public Map<String, Object> floordata() {
        List<Map<String, Object>> messageList = new ArrayList<>();
        List<Map<String, Object>> productList = new ArrayList<>();
        Map<String,Object> floorTitle = new HashMap<>();

        floorTitle.put("name","时尚女装");
        floorTitle.put("image_src","https://oss.ghovos.top/wechat-mini/public/pic_floor01_title.png");



        Map<String,Object>  map1= new HashMap<>();
        map1.put("name","优质服饰");
        map1.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_2@2x.png");
        map1.put("image_width","232");
        map1.put("open_type","navigate");
        map1.put("navigator_url","/pages/goods_list?query=服饰");

        Map<String,Object>  map2= new HashMap<>();
        map2.put("name","春季热门");
        map2.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_3@2x.png");
        map2.put("image_width","233");
        map2.put("open_type","navigate");
        map2.put("navigator_url","/pages/goods_list?query=热");

        Map<String,Object>  map3= new HashMap<>();
        map3.put("name","爆款清仓");
        map3.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_4@2x.png");
        map3.put("image_width","233");
        map3.put("open_type","navigate");
        map3.put("navigator_url","/pages/goods_list?query=热");

        Map<String,Object>  map4= new HashMap<>();
        map4.put("name","爆款清仓");
        map4.put("image_src","https://oss.ghovos.top/wechat-mini/upload/20201223_19214897.png");
        map4.put("image_width","233");
        map4.put("open_type","navigate");
        map4.put("navigator_url","/pages/goods_list?query=爆款");


        Map<String,Object>  map5= new HashMap<>();
        map5.put("name","怦然心动");
//        map5.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_2@2x.png");
        map5.put("image_src","https://oss.ghovos.top/wechat-mini/upload/20201223_13130640.png");
        map5.put("image_width","233");
        map5.put("open_type","navigate");
        map5.put("navigator_url","/pages/goods_list?query=心动");

        Map<String,Object>  map6= new HashMap<>();
        map6.put("name","怦然心动");
        map6.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_2@2x.png");
        map5.put("image_width","233");
        map6.put("open_type","navigate");
        map6.put("navigator_url","/pages/goods_list?query=心动");

        Map<String,Object>  map7= new HashMap<>();
        map7.put("name","怦然心动");
        map7.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_2@2x.png");
        map5.put("image_width","233");
        map7.put("open_type","navigate");
        map7.put("navigator_url","/pages/goods_list?query=心动");

        productList.add(map1);
        productList.add(map2);
        productList.add(map3);
//        productList.add(map4);
//        productList.add(map5);
        productList.add(map6);
        productList.add(map6);
        productList.add(map7);
        productList.add(map7);

    Map<String, Object> pMap = new HashMap<>();
        pMap.put("product_list",productList);

        pMap.put("floor_title",floorTitle);



        messageList.add(pMap);

    Map<String, Object> result = new HashMap<>();

    Map<String, Object> meta = new HashMap<>();
        meta.put("msg","获取成功");
        meta.put("status","200");


        result.put("message",messageList);
        result.put("meta",meta);
        return result;

}
*/


}
