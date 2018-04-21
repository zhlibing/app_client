package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.PublishLvxingzheResultModel;
import com.zlb.memo.mvvm.viewRequest.PublishLvxingzheRequestView;
import com.zlb.memo.mvvm.viewResult.PublishLvxingzheResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishLvxingzheRequestModel implements PublishLvxingzheRequestView {
    private PublishLvxingzheResultModel PublishLvxingzheResultModel;

    public PublishLvxingzheRequestModel(PublishLvxingzheResultView view) {
        PublishLvxingzheResultModel = new PublishLvxingzheResultModel(view);
    }

    @Override
    public void PublishLvxingzhe(Map<String, RequestBody> map) {
        PublishLvxingzheResultModel.PublishLvxingzheRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            PublishLvxingzheResultModel.view = null;
            PublishLvxingzheResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
