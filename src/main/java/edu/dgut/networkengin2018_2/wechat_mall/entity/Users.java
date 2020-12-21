package edu.dgut.networkengin2018_2.wechat_mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Users {

    private Integer userId;
    private String userName;
    private String userEmailCode;
    private String userIsActive;
    private String userSex;
    private String userQQ;
    private String userTel;
    private String userXueli;
    private String userHobby;
    private String userIntroduce;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date userCreateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date userUpdateTime;
    private String userToken;
}
