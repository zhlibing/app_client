package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.PublishShanghuResultModel;
import com.zlb.memo.mvvm.viewRequest.PublishShanghuRequestView;
import com.zlb.memo.mvvm.viewResult.PublishShanghuResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishShanghuRequestModel implements PublishShanghuRequestView {
    private PublishShanghuResultModel PublishShanghuResultModel;

    public PublishShanghuRequestModel(PublishShanghuResultView view) {
        PublishShanghuResultModel = new PublishShanghuResultModel(view);
    }

    @Override
    public void PublishShanghu(Map<String, RequestBody> map) {
        PublishShanghuResultModel.PublishShanghuRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            PublishShanghuResultModel.view = null;
            PublishShanghuResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
