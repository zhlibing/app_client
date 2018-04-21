package com.zlb.memo.api;

import com.vise.xsnow.common.GsonUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/3/27.
 */
public class JsonToBody {
    public static RequestBody ObjectToBody(Object ob) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), GsonUtil.gson().toJson(ob));
        return body;
    }
}
