package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.Result;
import edu.dgut.networkengin2018_2.wechat_mall.util.ResultGenerator;
import org.springframework.boot.web.server.Http2;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class GoodsController {
    @Resource
    private GoodsService goodsService;


    /**
     * 后台主界面
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
        request.setAttribute("path", "_goods");
        return "admin/goods";
    }

    /**
     * 得到商品列表
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
     * 商品编辑
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
/*
        if (newBeeMallGoods.getGoodsCategoryId() > 0) {
            if (newBeeMallGoods.getGoodsCategoryId() != null || newBeeMallGoods.getGoodsCategoryId() > 0) {
                //有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                GoodsCategory currentGoodsCategory = newBeeMallCategoryService.getGoodsCategoryById(newBeeMallGoods.getGoodsCategoryId());
                //商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (currentGoodsCategory != null && currentGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    //查询所有的一级分类
                    List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
                    //根据parentId查询当前parentId下所有的三级分类
                    List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentGoodsCategory.getParentId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    //查询当前三级分类的父级二级分类
                    GoodsCategory secondCategory = newBeeMallCategoryService.getGoodsCategoryById(currentGoodsCategory.getParentId());
                    if (secondCategory != null) {
                        //根据parentId查询当前parentId下所有的二级分类
                        List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                        //查询当前二级分类的父级一级分类
                        GoodsCategory firestCategory = newBeeMallCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            //所有分类数据都得到之后放到request对象中供前端读取
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentGoodsCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (newBeeMallGoods.getGoodsCategoryId() == 0) {
            //查询所有的一级分类
            List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                //查询一级分类列表中第一个实体的所有二级分类
                List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    //查询二级分类列表中第一个实体的所有三级分类
                    List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    request.setAttribute("firstLevelCategories", firstLevelCategories);
                    request.setAttribute("secondLevelCategories", secondLevelCategories);
                    request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }
        request.setAttribute("goods", newBeeMallGoods);
        request.setAttribute("path", "goods-edit");

 */
        return "admin/edit";
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
}
