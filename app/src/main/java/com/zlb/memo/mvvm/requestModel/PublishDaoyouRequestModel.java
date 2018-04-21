package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.PublishDaoyouResultModel;
import com.zlb.memo.mvvm.viewRequest.PublishDaoyouRequestView;
import com.zlb.memo.mvvm.viewResult.PublishDaoyouResultView;

import java.util.Map;

/**
 */
public class PublishDaoyouRequestModel implements PublishDaoyouRequestView {
    private PublishDaoyouResultModel PublishDaoyouResultModel;

    public PublishDaoyouRequestModel(PublishDaoyouResultView view) {
        PublishDaoyouResultModel = new PublishDaoyouResultModel(view);
    }

    @Override
    public void PublishDaoyou(Map<String, String> map) {
        PublishDaoyouResultModel.PublishDaoyouRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            PublishDaoyouResultModel.view = null;
            PublishDaoyouResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
