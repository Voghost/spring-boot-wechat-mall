package edu.dgut.networkengin2018_2.wechat_mall.entity;

import lombok.Data;

@Data
public class Ordersgoods {

    private Integer orderId;
    private Integer orderGoodsId;
    private String orderGoodsNumber;
    private Double orderPrice;
}
