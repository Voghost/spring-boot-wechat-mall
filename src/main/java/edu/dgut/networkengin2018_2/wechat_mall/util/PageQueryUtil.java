package edu.dgut.networkengin2018_2.wechat_mall.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用于页查询
 */
public class PageQueryUtil extends LinkedHashMap<String, Object> {
    //当前页码
    private int page;

    //每条页数
    private int limit;

    public PageQueryUtil(Map<String, Object> params) {
        //传入的关键字不只含有 page和limit  还有其他关键字
        this.putAll(params);

        this.page = Integer.parseInt(params.get("page").toString());
        this.limit= Integer.parseInt(params.get("limit").toString());
        // 如第一页 page=1 start=(1-1)*limit
        this.put("start", (page - 1) * limit);
        this.put("page",page);
        this.put("limit",limit);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "PageQueryUtil{" +
                "page=" + page +
                ", limit=" + limit +
                '}';
    }
}
