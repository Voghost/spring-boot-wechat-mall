package edu.dgut.networkengin2018_2.wechat_mall.entity;

import lombok.Data;

/**
 * 用于接受小程序传入的code
 */
@Data
public class WechatLogin {
    String code; //前端传入的code
}
