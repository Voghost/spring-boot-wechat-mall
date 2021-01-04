package edu.dgut.networkengin2018_2.wechat_mall.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Orders {

//    @ExcelProperty("订单id")
    @ExcelIgnore
    private Integer orderId;
    @ExcelProperty("订单用户id")
    private Integer orderUserId;
    @ExcelProperty("订单编号")
    private String orderNumber;         //订单编号
    @ExcelProperty("订单总价")
    private Double orderPrice;
    @ExcelProperty("订单状态")
    private Integer orderState;         //订单状态

    @ExcelProperty("订单提交日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderCreateTime;

    @ExcelProperty("订单修改日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderUpdateTime;

    @ExcelProperty("订单地址")
    private String orderAddress;
}
