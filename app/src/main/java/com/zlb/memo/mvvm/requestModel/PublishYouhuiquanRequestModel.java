package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.PublishYouhuiquanResultModel;
import com.zlb.memo.mvvm.viewRequest.PublishYouhuiquanRequestView;
import com.zlb.memo.mvvm.viewResult.PublishYouhuiquanResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishYouhuiquanRequestModel implements PublishYouhuiquanRequestView {
    private PublishYouhuiquanResultModel PublishYouhuiquanResultModel;

    public PublishYouhuiquanRequestModel(PublishYouhuiquanResultView view) {
        PublishYouhuiquanResultModel = new PublishYouhuiquanResultModel(view);
    }

    @Override
    public void PublishYouhuiquan(Map<String, RequestBody> map) {
        PublishYouhuiquanResultModel.PublishYouhuiquanRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            PublishYouhuiquanResultModel.view = null;
            PublishYouhuiquanResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
