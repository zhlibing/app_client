package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.PublishDarenResultModel;
import com.zlb.memo.mvvm.viewRequest.PublishDarenRequestView;
import com.zlb.memo.mvvm.viewResult.PublishDarenResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishDarenRequestModel implements PublishDarenRequestView {
    private PublishDarenResultModel PublishDarenResultModel;

    public PublishDarenRequestModel(PublishDarenResultView view) {
        PublishDarenResultModel = new PublishDarenResultModel(view);
    }

    @Override
    public void PublishDaren(Map<String, RequestBody> map) {
        PublishDarenResultModel.PublishDarenRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            PublishDarenResultModel.view = null;
            PublishDarenResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
