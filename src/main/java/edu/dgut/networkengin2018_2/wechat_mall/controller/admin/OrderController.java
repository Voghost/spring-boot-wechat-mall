package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Orders;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Ordersgoods;
import edu.dgut.networkengin2018_2.wechat_mall.service.OrderService;
import edu.dgut.networkengin2018_2.wechat_mall.util.DownExcel;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.Result;
import edu.dgut.networkengin2018_2.wechat_mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/orders")
    public String orderPage(HttpServletRequest request) {
        request.setAttribute("path", "orders");
        return "admin/order";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/orders/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(orderService.getOrderPage(pageUtil));
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/orders/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Orders order) {
        if (Objects.isNull(order.getOrderPrice())
                || Objects.isNull(order.getOrderId())
                || order.getOrderId() < 0
                || order.getOrderPrice() < 0) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = orderService.updateOrderInfo(order);
        if (result.equals("修改成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 配货
     */
    @RequestMapping(value = "/orders/checkDone", method = RequestMethod.POST)
    @ResponseBody
    public Result checkDone(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = orderService.checkOut(ids);
        if (result.equals("修改成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 关闭订单
     */
    @RequestMapping(value = "/orders/close", method = RequestMethod.POST)
    @ResponseBody
    public Result closeOrder(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = orderService.closeOrder(ids);
        if (result.equals("修改成功")) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/order-items/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        List<Ordersgoods>  ordersgoods=orderService.getOrderGoods(id);
        if (!CollectionUtils.isEmpty(ordersgoods)) {
            return ResultGenerator.genSuccessResult(ordersgoods);
        }
        return ResultGenerator.genFailResult("数据不存在");
    }

    /**
     * 订单下载
     */
    @RequestMapping("/orders/download")
    public void getExcel(HttpServletResponse response) throws IllegalAccessException, IOException,InstantiationException{
       List<Orders> ordersList = orderService.getAllOrdersForExcel();
        DownExcel.download(response,Orders.class,ordersList);
    }
}
