package edu.dgut.networkengin2018_2.wechat_mall.entity;

import lombok.Data;

@Data
public class Category {

    private Integer catId;
    private String catName;
    private Integer catPid;
    private Integer catLevel;
    private Boolean catDeleted;
    private String catIcon;
}
