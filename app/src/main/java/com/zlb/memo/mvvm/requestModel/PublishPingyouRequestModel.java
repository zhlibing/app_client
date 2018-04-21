package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.PublishPingyouResultModel;
import com.zlb.memo.mvvm.viewRequest.PublishPingyouRequestView;
import com.zlb.memo.mvvm.viewResult.PublishPingyouResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishPingyouRequestModel implements PublishPingyouRequestView {
    private PublishPingyouResultModel PublishPingyouResultModel;

    public PublishPingyouRequestModel(PublishPingyouResultView view) {
        PublishPingyouResultModel = new PublishPingyouResultModel(view);
    }

    @Override
    public void PublishPingyou(Map<String, RequestBody> map) {
        PublishPingyouResultModel.PublishPingyouRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            PublishPingyouResultModel.view = null;
            PublishPingyouResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
