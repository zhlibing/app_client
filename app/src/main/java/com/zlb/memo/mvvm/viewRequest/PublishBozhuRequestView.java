package com.zlb.memo.mvvm.viewRequest;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public interface PublishBozhuRequestView extends BaseRequestView {
    void PublishBozhu(Map<String, String> map);
}
