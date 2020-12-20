package edu.dgut.networkengin2018_2.wechat_mall.entity;

import lombok.Data;

@Data
public class Goodsattr {

    private Integer attrId;
    private Integer goodsId;
    private String attrValue;
    private Double attrPrice;
    private String attrName;
    private String attrSel;
    private String attrWrite;

}
