package edu.dgut.networkengin2018_2.wechat_mall.entity;

import lombok.Data;

@Data
public class Ordersgoods {

    private Integer orderId;            //订单id
    private Integer orderGoodsId;       //订单商品id
    private Integer orderGoodsNumber;    //订单商品数量
    private Double orderPrice; //订单价格
}
