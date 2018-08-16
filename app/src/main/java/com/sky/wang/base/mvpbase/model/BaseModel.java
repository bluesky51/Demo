package com.sky.wang.base.mvpbase.model;

/**
 * Created by bluesky on 2018/7/23.
 */

public class BaseModel<T> {
    private int code;
    private T data;
    private String msg;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
