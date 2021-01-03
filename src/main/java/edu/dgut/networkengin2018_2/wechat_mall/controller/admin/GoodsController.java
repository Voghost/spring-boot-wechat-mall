package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsCategoryService;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.Result;
import edu.dgut.networkengin2018_2.wechat_mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsCategoryService goodsCategoryService;



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
     * 商品主界面
     *
     * @param request
     * @return
     */
    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "goods"); //用户前端sider显示
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
        Result result = ResultGenerator.genSuccessResult(goodsService.getGoodsPage(pageUtil));
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
                || Objects.isNull(goods.getGoodsCatThreeId())
                || Objects.isNull(goods.getGoodsPicOne())
                || Objects.isNull(goods.getGoodsPicTwo())
                || Objects.isNull(goods.getGoodsPicThree())) {

            return ResultGenerator.genFailResult("参数异常！");
        }
        goods.setGoodsAddTime(new Date());
        goods.setGoodsUpdateTime(new Date());
        String result = goodsService.insertGoods(goods);
        if (result.equals("添加成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }

    }

    /**
     * 查询所有一级分类
     * 查询一级分类列表中第一个实体的所有二级分类
     * 查询二级分类列表中第一个实体的所有三级分类
     * 用于前端缓存
     *
     * @param request
     * @return
     */
    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        //查询所有的一级分类
        List<Category> firstLevelCategories = goodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0), 1);
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<Category> secondLevelCategories = goodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCatId()), 2);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<Category> thirdLevelCategories = goodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCatId()), 3);
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "goods-edit");
                return "admin/goods_edit";
            }
        }
        return "error/error_5xx";
    }


    /**
     * @param request
     * @param goodsId
     * @return
     */

    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Integer goodsId) {
        request.setAttribute("path", "edit");
        Goods goods = goodsService.getGoodsById(goodsId);
        //如果没有数据 跳转到404
        if (goods == null) {
            return "error/error_400";
        }

            List<Category> thirdLevelCategories = goodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(goods.getGoodsCatTwoId()), 3);
            List<Category> secondLevelCategories = goodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(goods.getGoodsCatOneId()), 2);
            List<Category> firstLevelCategories = goodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0), 1);


            //所有分类数据都得到之后放到request对象中供前端读取
            request.setAttribute("firstLevelCategories", firstLevelCategories);
            request.setAttribute("secondLevelCategories", secondLevelCategories);
            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
            request.setAttribute("firstLevelCategoryId", goods.getGoodsCatOneId());
            request.setAttribute("secondLevelCategoryId", goods.getGoodsCatTwoId());
            request.setAttribute("thirdLevelCategoryId", goods.getGoodsCatThreeId());


        request.setAttribute("goods", goods);
        request.setAttribute("path", "goods-edit");

        return "admin/goods_edit";
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
                || Objects.isNull(goods.getGoodsCatThreeId())
                || Objects.isNull(goods.getGoodsPicOne())
                || Objects.isNull(goods.getGoodsPicTwo())
                || Objects.isNull(goods.getGoodsPicThree())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        //设置更新时间
        goods.setGoodsUpdateTime(new Date());
        String result = goodsService.updateGoods(goods);
        if (result.equals("更新成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 获取商品详情详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        Goods goods = goodsService.getGoodsById(id);
        if (goods == null) {
            return ResultGenerator.genFailResult("数据库不存在");
        }
        return ResultGenerator.genSuccessResult(goods);
    }


    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        // 2 上架 1 下架 0删除
        if (sellStatus != 2 && sellStatus != 1 && sellStatus !=0) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (goodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

}
