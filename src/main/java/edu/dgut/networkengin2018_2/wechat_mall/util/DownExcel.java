package edu.dgut.networkengin2018_2.wechat_mall.util;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用于easyExcel的订单excel文件下载
 */
public class DownExcel {
    public static void download(HttpServletResponse response,
                                Class t,
                                List list)throws IOException, InstantiationException{
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition","attachment;filename=order.xls");
        EasyExcel.write(response.getOutputStream(),t).sheet("订单").doWrite(list);
    }
}
