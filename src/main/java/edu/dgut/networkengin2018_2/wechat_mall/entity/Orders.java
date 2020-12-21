package edu.dgut.networkengin2018_2.wechat_mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Orders {

    private Integer orderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
    private Double orderPrice;
    private String orderAddress;
    private String Authorization;
    private Integer orderUserId;
}
