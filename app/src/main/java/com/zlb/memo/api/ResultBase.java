package com.zlb.memo.api;

public class ResultBase<T> {
    public String code;

    public String message;

    public T data;
    //图片
    private Object datum;
}
