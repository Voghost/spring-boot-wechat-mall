package edu.dgut.networkengin2018_2.wechat_mall.entity;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 用于显示商品信息
 */


/*省去写getter setter*/
@Data
public class Goods {
    /**
     * 这里的数据类型用**对象**声明
     */
    private Integer goodsId;            //商品id
    private String goodsName;           //商品名称
    private Double goodsPrice;          //商品价格 取两位小数解决精度问题
    private Integer goodsNumber;        //商品数量
    private Double goodsWeight;         //商品重量 取两位小数
    private String goodsIntroduce;      //商品介绍
    private String goodsBigLogo;        //商品大logo : url
    private String goodsSmallLogo;      //商品小logo : url
    private String goodsState;          //商品状态 2:正常  1:缺货  0删除
    private Date goodsAddTime;          //商品添加时间
    private Date goodsUpdateTime;       //商品更新时间
    private Date goodsDeleteTime;       //商品删除时间
    private Integer goodsHotNumber;     //暂时不知道该字段用处
    private Boolean goodsIsPromote;     //商品是否促销
    private Integer goodsCatOneId;      //商品第一级id
    private Integer goodsCatTwoId;      //商品第二级id
    private Integer goodsCatThreeId;    //商品第三级id
}

