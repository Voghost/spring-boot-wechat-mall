package edu.dgut.networkengin2018_2.wechat_mall.controller.wechatapi;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Orders;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Ordersgoods;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Users;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.service.OrderService;
import edu.dgut.networkengin2018_2.wechat_mall.service.OrdersGoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.activation.CommandInfo;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechatapi")
public class OrderApiController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private OrdersGoodsService ordersGoodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 创建新的订单
     *
     * @param authorization
     * @param map
     * @return
     */
    @PostMapping("/my/orders/create")
    @ResponseBody
    public Map<String, Object> create(@RequestHeader("Authorization") String authorization,
                                      @RequestBody Map<String, Object> map) {
        Orders orders = new Orders();
        Users userTemp = usersService.getUserByToken(authorization);
        List<Ordersgoods> ordersGoodsList = new ArrayList<>();

        //判断数据库中是否有这个用户，并且这个用户有无被限制
        if (userTemp == null || userTemp.getUserId() == null || userTemp.getUserIsActive() == false) {
            Map<String, Object> result = new HashMap<>();
            Map<String, Object> meta = new HashMap<>();
            meta.put("msg", "error");
            meta.put("code", 400);
            result.put("message", meta);
            return result;
        }


//        String tmp = (String)map.get("consignee_addr");
        /* if (map.get("consignee_addr") != null *//*&& (String)map.get("consignee_addr") !=""*//*) {
            String userName = (String) ((Map<String, Object>) map.get("consignee_addr")).get("userName"); //名字
            String telNumber = (String) ((Map<String, Object>) map.get("consignee_addr")).get("telNumber"); //电话
            String postalCode = (String) ((Map<String, Object>) map.get("consignee_addr")).get("postalCode"); //邮政编码
            String provinceName = (String) ((Map<String, Object>) map.get("consignee_addr")).get("provinceName"); //省
            String cityName = (String) ((Map<String, Object>) map.get("consignee_addr")).get("cityName"); //市
            String countyName = (String) ((Map<String, Object>) map.get("consignee_addr")).get("countyName"); //县
            String detailInfo = (String) ((Map<String, Object>) map.get("consignee_addr")).get("detailInfo"); //具体地址
            String address = provinceName + cityName + countyName + detailInfo + userName + "电话:" + telNumber + " 邮政编码:" + postalCode; //完整地址
            orders.setOrderAddress(address); //不为空设置地址
        }
*/
        ArrayList<Map<String, Object>> goods = (ArrayList<Map<String, Object>>) map.get("goods");

        double totalPrice = 0;
        for (int i = 0; i < goods.size(); i++) {
            Ordersgoods ordersGoodsTmp = new Ordersgoods();
//            ordersGoodsTmp.setOrderGoodsId(Integer.parseInt((String) goods.get(i).get("goods_id")));
            ordersGoodsTmp.setOrderGoodsId((Integer) goods.get(i).get("goods_id")); // 修改后
            ordersGoodsTmp.setOrderGoodsNumber((Integer) goods.get(i).get("goods_number"));
            ordersGoodsTmp.setOrderPrice(((Integer) goods.get(i).get("goods_price")).doubleValue());
            ordersGoodsList.add(ordersGoodsTmp);
            totalPrice = totalPrice + (ordersGoodsTmp.getOrderPrice() * ordersGoodsTmp.getOrderGoodsNumber());
        }

        orders.setOrderUserId(userTemp.getUserId()); //设置用户id
        orders.setOrderPrice(totalPrice);
        orderService.createOrder(orders);   //先创建订单

        for (int i = 0; i < goods.size(); i++) {
            ordersGoodsList.get(i).setOrderId(orders.getOrderId());
        }

        ordersGoodsService.createOrderToGoods(ordersGoodsList);

        Map<String, Object> message = new HashMap<>();

        message.put("order_id", orders.getOrderId());
        message.put("user_id", orders.getOrderUserId());
        message.put("order_number", orders.getOrderNumber());
        message.put("order_price", orders.getOrderPrice());
        message.put("create_time", orders.getOrderCreateTime().getTime() / 1000);
        message.put("update_time", orders.getOrderUpdateTime().getTime() / 1000);

        Map<String, Object> meta = new HashMap<>();
        meta.put("msg", "创建订单成功");
        meta.put("status", "200");

        Map<String, Object> result = new HashMap<>();
        result.put("message", message);
        result.put("meta", meta);

        return result;
    }

    /**
     * 查询用户相关订单
     *
     * @param authorization
     * @param type
     * @return
     */
    @GetMapping("/my/orders/all")
    @ResponseBody
    public Map<String, Object> getMyAllOrder(@RequestHeader("Authorization") String authorization,
                                             @RequestParam("type") Integer type) {
        Users userTemp = usersService.getUserByToken(authorization);
        List<Ordersgoods> ordersGoodsList = new ArrayList<>();

        //判断数据库中是否有这个用户，并且这个用户有无被限制
        if (userTemp == null || userTemp.getUserId() == null || userTemp.getUserIsActive() == false) {
            Map<String, Object> result = new HashMap<>();
            Map<String, Object> meta = new HashMap<>();
            meta.put("msg", "error");
            meta.put("code", 400);
            result.put("meta", meta);
            return result;
        }

        Integer userId = userTemp.getUserId();
        if (type == 1) {
            type = null; //全部
        } else if (type == 2) {
            type = 0; //未支付
        } else if (type == 3) {
            type = 1; //支付为发货
        }

        List<Orders> ordersList = orderService.getOrderByUserId(userTemp.getUserId(), type); //订单id
        if (ordersList == null) {
            Map<String, Object> meta = new HashMap<>();
            meta.put("msg", "获取订单列表成功");
            meta.put("status", 200);
            Map<String, Object> message = new HashMap<>();
            message.put("total", 0);
            message.put("orders", null);


            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("message", message);
            pageResult.put("meta", meta);
            return pageResult;
        }

        List<Map<String, Object>> ordersResult = new ArrayList<>();
        for (int i = 0; i < ordersList.size(); i++) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("order_id", ordersList.get(i).getOrderId());
            temp.put("user_id", ordersList.get(i).getOrderUserId());
            temp.put("order_number", ordersList.get(i).getOrderNumber());
            temp.put("order_price", ordersList.get(i).getOrderPrice());
            temp.put("consignee_addr", ordersList.get(i).getOrderAddress());
            temp.put("create_time", ordersList.get(i).getOrderCreateTime().getTime() / 1000);
            temp.put("update_time", ordersList.get(i).getOrderUpdateTime().getTime() / 1000);

            List<Ordersgoods> goods = ordersGoodsService.getOrderGoodsById(ordersList.get(i).getOrderId()); //从数据库里获取到的商品列表

            List<Map<String, Object>> goodsResult = new ArrayList<>(); //商品列表
            Double totalPrice = 0.0;
            for (int j = 0; j < goods.size(); j++) {
                Map<String, Object> goodsTemp = new HashMap<>();
                Goods mygoods = goodsService.getGoodsById(goods.get(j).getOrderGoodsId());


                goodsTemp.put("goods_id", mygoods.getGoodsId());
                goodsTemp.put("order_id", ordersList.get(i).getOrderId());
                goodsTemp.put("goods_price", goods.get(j).getOrderPrice());
                goodsTemp.put("goods_number", goods.get(j).getOrderGoodsNumber());
                goodsTemp.put("goods_total_price", goods.get(j).getOrderGoodsNumber() * goods.get(j).getOrderPrice());
                goodsTemp.put("goods_name", mygoods.getGoodsName());
                goodsTemp.put("goods_small_logo", mygoods.getGoodsSmallLogo());

                goodsResult.add(goodsTemp);
                totalPrice = totalPrice + goods.get(j).getOrderPrice() * goods.get(j).getOrderPrice(); //数量 × 价格
            }

            temp.put("goods", goodsResult);
            temp.put("total_count", goodsResult.size()); //商品的数量
            temp.put("total_price", totalPrice); //所有商品总价
            ordersResult.add(temp);
        }

        Map<String, Object> message = new HashMap<>();
        message.put("count", ordersResult.size());
        message.put("orders", ordersResult);

        Map<String, Object> meta = new HashMap<>();
        meta.put("msg", "获取订单列表成功");
        meta.put("status", 200);

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("message", message);
        pageResult.put("meta", meta);

        return pageResult;
    }

    /**
     * 查询用户的订单状态
     *
     * @param map
     * @return
     */
    @PostMapping("/my/orders/chkOrder")
    @ResponseBody
    public Map<String, Object> checkOrder(@RequestHeader(value = "Authorization") String authorization,
                                          @RequestBody Map<String, Object> map) {

        String orderNumber = (String) map.get("order_number");

        Users userTemp = usersService.getUserByToken(authorization);
        List<Ordersgoods> ordersGoodsList = new ArrayList<>();

        //判断数据库中是否有这个用户，并且这个用户有无被限制
        if (userTemp == null || userTemp.getUserId() == null || userTemp.getUserIsActive() == false) {
            Map<String, Object> result = new HashMap<>();
            Map<String, Object> meta = new HashMap<>();
            meta.put("msg", "error");
            meta.put("code", 400);
            result.put("meta", meta);
            return result;
        }


        Orders orders = orderService.getOrderByNumber(orderNumber);
        //无数据

        if (orders.getOrderState() > 0) {
            Map<String, Object> meta = new HashMap<>();
            meta.put("msg", "验证成功");
            meta.put("status", 200);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("message", "支付成功");
            pageResult.put("meta", meta);
            return pageResult;
        } else if (orders.getOrderState() == 0) {
            Map<String, Object> meta = new HashMap<>();
            meta.put("msg", "验证失败");
            meta.put("status", 400);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("message", "未支付");
            pageResult.put("meta", meta);
            return pageResult;

        }

        Map<String, Object> meta = new HashMap<>();
        meta.put("msg", "error");
        meta.put("status", 400);
        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("message", null);
        pageResult.put("meta", meta);
        return pageResult;
    }

    @PostMapping("/my/orders/pay")
    @ResponseBody
    public Map<String, Object> pay(@RequestHeader(value = "Authorization") String authorization,
                                   @RequestBody Map<String, Object> map) {

        String orderNumber = (String) map.get("order_number");
        Users userTemp = usersService.getUserByToken(authorization);
        List<Ordersgoods> ordersGoodsList = new ArrayList<>();

        //判断数据库中是否有这个用户，并且这个用户有无被限制
        if (userTemp == null || userTemp.getUserId() == null || userTemp.getUserIsActive() == false) {
            Map<String, Object> result = new HashMap<>();
            Map<String, Object> meta = new HashMap<>();
            meta.put("msg", "error");
            meta.put("code", 400);
            result.put("meta", meta);
            return result;
        }


//        int num = orderService.updateOrderStatusByNumber("1", 1); //1 为已支付状态
        int num = orderService.updateOrderStatusByNumber(orderNumber, 1); //1 为已支付状态
        //如果受影响行数大于０　支付成功
        if (num > 0) {
            Map<String, Object> result = new HashMap<>();
            Map<String, Object> meta = new HashMap<>();
            Map<String, Object> message = new HashMap<>();
            meta.put("msg", "支付成功");
            meta.put("code", 200);
            message.put("pay", 1);  //pay =1 支付成功

            result.put("meta", meta);
            result.put("message", message);

            return result;
        }

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        meta.put("msg", "支付失败");
        meta.put("code", 200);
        message.put("pay", 0);  //pay =1 支付成功

        result.put("meta", meta);
        result.put("message", message);

        return result;


    }


}
