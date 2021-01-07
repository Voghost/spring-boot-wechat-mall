package edu.dgut.networkengin2018_2.wechat_mall.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.dgut.networkengin2018_2.wechat_mall.util.IpUtils;
import edu.dgut.networkengin2018_2.wechat_mall.util.Result;
import edu.dgut.networkengin2018_2.wechat_mall.util.ResultGenerator;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Map;

@Controller
public class TCController {


    private static final String APP_ID = "2000719962";
    private static final String APP_SECRET = "02vJmFOYkwSgmFvHWFBwUhg**";
    private static final String VERIFY_URI = "https://ssl.captcha.qq.com/ticket/verify?aid=%s&AppSecretKey=%s&Ticket=%s&Randstr=%s&UserIP=%s";

    @RequestMapping(value = "/tc", method = RequestMethod.POST)
    @ResponseBody
    public Result verifyTicket(@RequestBody Map<String, Object> map, HttpServletRequest request) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        final String IP = IpUtils.getIpAddr(request);
        HttpGet httpGet;
        CloseableHttpResponse response = null;
        try {
            httpGet = new HttpGet(String.format(VERIFY_URI,
                    APP_ID,
                    APP_SECRET,
                    URLEncoder.encode((String) map.get("ticket"), "UTF-8"),
                    URLEncoder.encode((String) map.get("randStr"), "UTF-8"),
                    URLEncoder.encode(IP, "UTF-8")
            ));
            response = httpclient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String res = EntityUtils.toString(entity);
                System.out.println(res); // 临时输出

                JSONObject result = JSON.parseObject(res);
                // 返回码
                int code = result.getInteger("response");
                // 恶意等级
                int evilLevel = result.getInteger("evil_level");

                // 验证成功
                if (code == 1){
                    return ResultGenerator.genSuccessResult(result);
                }else{
                    return ResultGenerator.genFailResult("验证失败");
                }
            }
        } catch (java.io.IOException e) {
            // 忽略
        } finally {
            try {
                response.close();
            } catch (Exception ignore) {
            }
        }

        return ResultGenerator.genFailResult("验证失败");
    }

}

