package com.zlb.memo.mvvm.viewRequest;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public interface PublishDarenRequestView extends BaseRequestView {
    void PublishDaren(Map<String, RequestBody> map);
}
