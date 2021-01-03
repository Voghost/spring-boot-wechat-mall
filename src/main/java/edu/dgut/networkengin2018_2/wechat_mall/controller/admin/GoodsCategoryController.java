package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsCategoryService;
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

    /**
     * 列表
     */
    @RequestMapping(value = "/categories/listForSelect", method = RequestMethod.GET)
    @ResponseBody
    public Result listForSelect(@RequestParam("categoryId") Integer categoryId) {
        if (categoryId == null || categoryId < 1) {
            return ResultGenerator.genFailResult("缺少参数！");
        }
        Category category = goodsCategoryService.getGoodsCategoryById(categoryId);
        //既不是一级分类也不是二级分类则为不返回数据
        if (category == null || category.getCatLevel() == 3) {
            return ResultGenerator.genFailResult("参数异常！");
        }


        Map categoryResult = new HashMap(2);

        //如果是一级分类则返回当前一级分类下的所有二级分类，以及二级分类列表中第一条数据下的所有三级分类列表
        if (category.getCatLevel() == 1) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<Category> secondLevelCategories = goodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), 2);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<Category> thirdLevelCategories = goodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCatId()), 3);
                categoryResult.put("secondLevelCategories", secondLevelCategories);
                categoryResult.put("thirdLevelCategories", thirdLevelCategories);
            }
        }
        if (category.getCatLevel() == 2) {
            //如果是二级分类则返回当前分类下的所有三级分类列表
            List<Category> thirdLevelCategories = goodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), 3);
            categoryResult.put("thirdLevelCategories", thirdLevelCategories);
        }
        return ResultGenerator.genSuccessResult(categoryResult);
    }



    /**
     * 批量删除
     */
    @RequestMapping(value = "/categories/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (goodsCategoryService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}
