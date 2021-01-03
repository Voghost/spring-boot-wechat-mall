package edu.dgut.networkengin2018_2.wechat_mall.entity;

import lombok.Data;


@Data
public class MyUser {
    private Integer userId;   //用户id
    private String userName;  //用户名称
    private String password;  //用户密码
    private String authority; //用户权利, 暂时只有 ROLE_ADMIN 和 ROLE_USER
}


