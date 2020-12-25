package edu.dgut.networkengin2018_2.wechat_mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Orders {

    private Integer orderId;
    private Integer orderUserId;
    private String orderNumber;         //订单编号
    private Double orderPrice;
    private Integer orderState;         //订单状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderCreateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderUpdateTime;
    private String orderAddress;
}
