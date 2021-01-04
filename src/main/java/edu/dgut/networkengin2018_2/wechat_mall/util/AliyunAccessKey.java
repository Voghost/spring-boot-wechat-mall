package edu.dgut.networkengin2018_2.wechat_mall.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * 获取阿里云oss的key word
 */
public class AliyunAccessKey {
    private String accessKeyId;
    private String accessKeySecret;

    public AliyunAccessKey(){
        Reader reader = null;
        try {

            // 服务器路径
            reader = new FileReader("/home/voghost/someServer/messages/other/AccessKey.csv");
            //本地测试路径
//            reader = new FileReader("src/main/resources/other/AccessKey.csv");
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
            this.accessKeyId = record.get(0);
            this.accessKeySecret = record.get(1);
        }

        //关闭函数
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }
}
