package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.PublishBozhuResultModel;
import com.zlb.memo.mvvm.viewRequest.PublishBozhuRequestView;
import com.zlb.memo.mvvm.viewResult.PublishBozhuResultView;

import java.util.Map;

/**
 */
public class PublishBozhuRequestModel implements PublishBozhuRequestView {
    private PublishBozhuResultModel PublishBozhuResultModel;

    public PublishBozhuRequestModel(PublishBozhuResultView view) {
        PublishBozhuResultModel = new PublishBozhuResultModel(view);
    }

    @Override
    public void PublishBozhu(Map<String, String> map) {
        PublishBozhuResultModel.PublishBozhuRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            PublishBozhuResultModel.view = null;
            PublishBozhuResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
