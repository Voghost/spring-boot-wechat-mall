package edu.dgut.networkengin2018_2.wechat_mall.util;

import com.alibaba.excel.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {

    /**
     * 获取IP地址 *
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            if (request == null) {
                return "";
            }
            ip = request.getHeader("x-forwarded-for");
            if (checkIp(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (checkIp(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (checkIp(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (checkIp(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (checkIp(ip)) {
                ip = request.getRemoteAddr();
                if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                    // 根据网卡取本机配置的IP
                    ip = getLocalAddr();
                }
            }
        } catch (Exception e) {
        }

        //使用代理，则获取第一个IP地址
        if (StringUtils.isEmpty(ip) && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }

        return ip;
    }

    private static boolean checkIp(String ip) {
        String unknown = "unknown";
        return StringUtils.isEmpty(ip) || ip.length() == 0 || unknown.equalsIgnoreCase(ip);
    }

    /**
     * 获取本机的IP地址
     */
    private static String getLocalAddr() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

        }
        return "";
    }
}