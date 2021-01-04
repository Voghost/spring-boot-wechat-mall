package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;


import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Orders;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.service.OrderService;
import edu.dgut.networkengin2018_2.wechat_mall.service.OrdersGoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private OrderService orderService;
    /**
     * 后台主界面
     *
     * @param request
     * @return
     */
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request, Model model) {
        request.setAttribute("path", "index");

        List<Orders> ordersList = orderService.getAllOrdersForExcel();
        double totalOrdersPrice = ordersList.parallelStream().mapToDouble(Orders::getOrderPrice).sum();// 商品总价
        model.addAttribute("ordersNumber",ordersList.size());
        model.addAttribute("totalOrdersPrice",totalOrdersPrice);
        model.addAttribute("userNumber",usersService.getCountOfUser()); //用户数量
        return "admin/index";
    }

    /**
     * 登录界面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "/admin/login";
    }



}
