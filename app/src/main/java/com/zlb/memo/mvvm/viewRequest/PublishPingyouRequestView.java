package com.zlb.memo.mvvm.viewRequest;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public interface PublishPingyouRequestView extends BaseRequestView {
    void PublishPingyou(Map<String, RequestBody> map);
}
