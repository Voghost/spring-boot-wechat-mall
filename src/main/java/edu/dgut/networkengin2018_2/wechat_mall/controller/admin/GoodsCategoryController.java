package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsCategoryService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.Result;
import edu.dgut.networkengin2018_2.wechat_mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class GoodsCategoryController {

    @Resource
    private GoodsCategoryService goodsCategoryService;

    /**
     * 分类管理页面
     *
     * @param request
     * @param categoryLevel
     * @param parentId
     * @param backParentId
     * @return
     */
    @GetMapping("/categories")
    public String categoriesPage(HttpServletRequest request,
                                 @RequestParam("categoryLevel") Integer categoryLevel, /*目录层级*/
                                 @RequestParam("parentId") Integer parentId, /*目录的父id*/
                                 @RequestParam("backParentId") Integer backParentId /*用于前端快速回到父id*/) {
        if (categoryLevel == null || categoryLevel < 1 || categoryLevel > 3) {
            return "error/error_5xx";
        }
        request.setAttribute("path", "category"); //用于前端表示 在哪个sider中
        request.setAttribute("parentId", parentId);
        request.setAttribute("backParentId", backParentId);
        request.setAttribute("categoryLevel", categoryLevel);
        return "admin/category";

    }

    /**
     * 列表
     */
    @RequestMapping(value = "/categories/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page"))
                || StringUtils.isEmpty(params.get("limit"))
                || StringUtils.isEmpty(params.get("categoryLevel"))
                || StringUtils.isEmpty(params.get("parentId"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(goodsCategoryService.getCategoriesPage(pageUtil));
    }


    /**
     * 添加分类
     */
    @RequestMapping(value = "/categories/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Category category) {
        if (Objects.isNull(category.getCatLevel())
                || StringUtils.isEmpty(category.getCatName())
                || Objects.isNull(category.getCatPid())
                || Objects.isNull(category.getCatIcon())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = goodsCategoryService.insertCategory(category);
        if (result.equals("插入成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 修改分类
     */
    @RequestMapping(value = "/categories/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Category category) {
        if (Objects.isNull(category.getCatId())
                || Objects.isNull(category.getCatLevel())
                || StringUtils.isEmpty(category.getCatName())
                || Objects.isNull(category.getCatPid())
                || Objects.isNull(category.getCatIcon())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = goodsCategoryService.updateGoodsCategory(category);
        if (result.equals("成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 查询分类详情
     */
    @GetMapping("/categories/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        Category goodsCategory = goodsCategoryService.getGoodsCategoryById(id);
        if (goodsCategory == null) {
            return ResultGenerator.genFailResult("未查询到数据");
        }
        return ResultGenerator.genSuccessResult(goodsCategory);
    }

}
