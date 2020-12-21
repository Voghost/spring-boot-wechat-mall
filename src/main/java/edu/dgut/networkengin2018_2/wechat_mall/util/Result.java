package edu.dgut.networkengin2018_2.wechat_mall.util;

import java.io.Serializable;

/**
 * 模板结果类
 * 添加返回的信息
 * @param <T>
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int resultCode; //返回结果码
    private String message; //返回结果信息
    private T data; //模板 在各种数据间通用

    public Result() {
    }

    public Result(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
