package edu.dgut.networkengin2018_2.wechat_mall.util;

import java.io.Serializable;
import java.util.Map;

public class ResultForWechat {
    /**
     * 模板结果类
     * 添加返回的信息
     *
     * @param <T>
     */

    public class Result<T> implements Serializable {
        private static final long serialVersionUID = 1L;
        private Map<String, Object> meta;
        private T message; //模板 在各种数据间通用

        public Result() {
        }

        public Result(int resultCode, String message) {
            this.meta.put("msg", "获取成功");
            this.meta.put("status", "获取成功");
        }

        public Map<String, Object> getMeta() {
            return meta;
        }

        public void setMeta(Map<String, Object> meta) {
            this.meta = meta;
        }

        public T getMessage() {
            return message;
        }

        public void setMessage(T message) {
            this.message = message;
        }
    }
}
