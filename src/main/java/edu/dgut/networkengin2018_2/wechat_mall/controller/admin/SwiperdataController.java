package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Swiperdata;
import edu.dgut.networkengin2018_2.wechat_mall.service.SwiperdataService;
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
public class SwiperdataController {

    @Resource
    private SwiperdataService swiperdataService;

    @GetMapping("/swiperdatas")
    public String swiperdataPage(HttpServletRequest request){
        request.setAttribute("path","swiperdata");
        return "admin/swiperdata";
    }

    @RequestMapping(value = "/swiperdatas/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(swiperdataService.getSwiperdataPage(pageUtil));
    }

    @RequestMapping(value = "/swiperdatas/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Swiperdata swiperdata) {
        if (StringUtils.isEmpty(swiperdata.getNavigatorUrl())
                || Objects.isNull(swiperdata.getImageSrc())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = swiperdataService.insertSwiperdata(swiperdata);
        if (result.equals("插入成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @RequestMapping(value = "/swiperdatas/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Swiperdata swiperdata) {
        if (Objects.isNull(swiperdata.getSwiperId())
                || StringUtils.isEmpty(swiperdata.getNavigatorUrl())
                || Objects.isNull(swiperdata.getImageSrc())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = swiperdataService.updateSwiperdata(swiperdata);
        if (result.equals("修改成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @GetMapping("/swiperdatas/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        Swiperdata swiperdata = swiperdataService.getSwiperdataById(id);
        if (swiperdata == null) {
            return ResultGenerator.genFailResult("未查到数据");
        }
        return ResultGenerator.genSuccessResult(swiperdata);
    }

    @RequestMapping(value = "/swiperdatas/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (swiperdataService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
