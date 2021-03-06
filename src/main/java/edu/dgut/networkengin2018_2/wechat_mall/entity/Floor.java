package edu.dgut.networkengin2018_2.wechat_mall.entity;

import lombok.Data;

@Data
public class Floor {
    Integer floorId; //楼层id
    String floorName; //楼层名字
    String floorTitleImage; //楼层标题url
    String floorKeyword;  //楼层关键字,用于商品查询
    Integer floorWeight; //楼层权重
}
