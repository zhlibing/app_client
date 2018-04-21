package com.zlb.memo.mvvm.viewRequest;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public interface PublishShanghuRequestView extends BaseRequestView {
    void PublishShanghu(Map<String, RequestBody> map);
}
