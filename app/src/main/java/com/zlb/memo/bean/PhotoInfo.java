package com.zlb.memo.bean;

import java.io.Serializable;

/**
 * Created by suneee on 2016/11/17.
 */
public class PhotoInfo implements Serializable {

    public String url;
    public int w;
    public int h;

    public PhotoInfo(String url) {
        this.url = url;
    }

    public PhotoInfo() {
    }
}
