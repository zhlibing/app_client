package com.zlb.memo.mvvm.viewRequest;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public interface PublishYouhuiquanRequestView extends BaseRequestView {
    void PublishYouhuiquan(Map<String, RequestBody> map);
}
