package com.zhuguangmama.imagepicker.bean;

public interface CompressImageInfoGetter {
    void setParamsName(String paramsName);

    String imageFilePath();

    String imageFileName();

    String requestPramsName();

}