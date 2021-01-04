package edu.dgut.networkengin2018_2.wechat_mall.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * 用户获取文件中微信个人信息
 */
public class WechatInfo {
  public String  appID;
  public String appSecret;

  public WechatInfo(){
    Reader reader = null;
    try {
      reader = new FileReader("/home/voghost/someServer/messages/other/Wechat.csv");
//      reader = new FileReader("src/main/resources/other/Wechat.csv");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    Iterable<CSVRecord> records = null;
    try {
      records = CSVFormat.RFC4180.parse(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    for (CSVRecord record : records) {
      this.appID= record.get(0);
      this.appSecret= record.get(1);
    }

    //关闭函数
    try {
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getAppID() {
    return appID;
  }

  public void setAppID(String appID) {
    this.appID = appID;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }
}
