package edu.dgut.networkengin2018_2.wechat_mall.entity;

import lombok.Data;

@Data
public class FloorProduct {
    Integer floorProductId;         //产品id
    Integer floorId;                //对应的楼层id
    String floorProductName;        //产品名称
    String floorProductImageUrl;    //产品图片链接
    String floorProductOpenType;    //产品打开方式
    String floorProductKeyword;     //产品关键字
}
