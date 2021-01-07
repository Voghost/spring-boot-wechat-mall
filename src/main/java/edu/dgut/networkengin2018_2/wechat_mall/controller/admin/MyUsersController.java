package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.entity.MyUser;
import edu.dgut.networkengin2018_2.wechat_mall.service.MyUsersService;
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
public class MyUsersController {

    @Resource
    private MyUsersService myUsersService;

    @GetMapping("/myusers")
    public String myusersPage(HttpServletRequest request){
        request.setAttribute("path","myusers");
        return "admin/myusers";
    }

    @RequestMapping(value = "/myusers/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(myUsersService.getMyUsersPage(pageUtil));
    }

    @RequestMapping(value = "/myusers/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody MyUser myUser) {
        if (StringUtils.isEmpty(myUser.getUserName())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = myUsersService.insertMyUsers(myUser);
        if (result.equals("插入成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @RequestMapping(value = "/myusers/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody MyUser myUser) {
        if (Objects.isNull(myUser.getUserId())
                || StringUtils.isEmpty(myUser.getUserName())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = myUsersService.updateMyUsers(myUser);
        if (result.equals("修改成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @GetMapping("/myusers/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        MyUser myUser = myUsersService.getMyUsersById(id);
        if (myUser == null) {
            return ResultGenerator.genFailResult("未查到数据");
        }
        return ResultGenerator.genSuccessResult(myUser);
    }

    @RequestMapping(value = "/myusers/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (myUsersService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
