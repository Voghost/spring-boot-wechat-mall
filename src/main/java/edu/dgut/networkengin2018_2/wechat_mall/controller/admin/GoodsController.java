package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.Result;
import edu.dgut.networkengin2018_2.wechat_mall.util.ResultGenerator;
import org.springframework.boot.web.server.Http2;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    /**
     * 商品主界面
     *
     * @param request
     * @return
     */
    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "_goods");
        return "admin/goods";
    }

    /**
     * 得到商品列表
     *
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping("/goods/list")
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(goodsService.getGoodsPage(pageUtil));
    }


    /**
     * 添加商品
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Goods goods) {
        //判断传入信息是否为空
        if (StringUtils.isEmpty(goods.getGoodsName())
                || StringUtils.isEmpty(goods.getGoodsIntroduce())
                || StringUtils.isEmpty(goods.getGoodsBigLogo())
                || StringUtils.isEmpty(goods.getGoodsSmallLogo())
                || StringUtils.isEmpty(goods.getGoodsState())
                || Objects.isNull(goods.getGoodsPrice())
                || Objects.isNull(goods.getGoodsNumber())
                || Objects.isNull(goods.getGoodsWeight())
                || Objects.isNull(goods.getGoodsIsPromote())
                || Objects.isNull(goods.getGoodsCatOneId())
                || Objects.isNull(goods.getGoodsCatTwoId())
                || Objects.isNull(goods.getGoodsCatThreeId())) {

            return ResultGenerator.genFailResult("参数异常！");
        }

        String result = goodsService.insertGoods(goods);
        if (result.equals("添加成功")){
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }

    }


    /**
     * 修改
     * 必须要有id
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Goods goods) {
        if (Objects.isNull(goods.getGoodsId())
                || StringUtils.isEmpty(goods.getGoodsIntroduce())
                || StringUtils.isEmpty(goods.getGoodsBigLogo())
                || StringUtils.isEmpty(goods.getGoodsSmallLogo())
                || StringUtils.isEmpty(goods.getGoodsState())
                || Objects.isNull(goods.getGoodsPrice())
                || Objects.isNull(goods.getGoodsNumber())
                || Objects.isNull(goods.getGoodsWeight())
                || Objects.isNull(goods.getGoodsIsPromote())
                || Objects.isNull(goods.getGoodsCatOneId())
                || Objects.isNull(goods.getGoodsCatTwoId())
                || Objects.isNull(goods.getGoodsCatThreeId())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = goodsService.updateGoods(goods);
        if (result.equals("修改成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


}
