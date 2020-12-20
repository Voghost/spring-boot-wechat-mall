package edu.dgut.networkengin2018_2.wechat_mall.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {

    private Integer orderId;
    private Date orderTime;
    private Double orderPrice;
    private String orderAddress;
    private String Authorization;
    private Integer orderUserId;
}
