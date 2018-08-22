package com.letu.app.baselib.base;


import com.google.gson.annotations.Expose;


public class BaseResponse<T> {

    @Expose
    private String message;
    @Expose
    private int code;
    @Expose
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
}
