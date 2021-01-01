package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.service.FloorService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.Result;
import edu.dgut.networkengin2018_2.wechat_mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class FloorController {

    @Resource
    private FloorService floorService;

    /**
     * 楼层主界面界面
     * @param request
     * @return
     */
    @GetMapping("/floor")
    public String floorPage(HttpServletRequest request) {
        request.setAttribute("path", "floor"); //用户前端sider显示
        return "admin/floor";
    }

    @ResponseBody
    @GetMapping("/floor/list")
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(floorService.getFloorPage(pageUtil));
    }


    @RequestMapping(value = "/floor/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (floorService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}
