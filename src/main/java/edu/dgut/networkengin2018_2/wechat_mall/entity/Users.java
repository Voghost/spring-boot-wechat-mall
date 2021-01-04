package edu.dgut.networkengin2018_2.wechat_mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Users {

    private Integer userId;

    private String userOpenId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date userCreateTime;     //用户创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date userUpdateTime;     //用户修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date userLastLoginTime; //用户最后登录时间

    private Boolean userIsActive; // 用户是否激活

    private String userToken;     //
}
