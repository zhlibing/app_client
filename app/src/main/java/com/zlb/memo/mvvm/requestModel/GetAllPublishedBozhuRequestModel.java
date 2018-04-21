package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.GetAllPublishedBozhuResultModel;
import com.zlb.memo.mvvm.viewRequest.GetAllPublishedBozhuRequestView;
import com.zlb.memo.mvvm.viewResult.GetAllPublishedBozhuResultView;

import java.util.Map;

/**
 */
public class GetAllPublishedBozhuRequestModel implements GetAllPublishedBozhuRequestView {
    private GetAllPublishedBozhuResultModel GetAllPublishedBozhuResultModel;

    public GetAllPublishedBozhuRequestModel(GetAllPublishedBozhuResultView view) {
        GetAllPublishedBozhuResultModel = new GetAllPublishedBozhuResultModel(view);
    }

    @Override
    public void GetAllPublishedBozhu(Map<String, String> map) {
        GetAllPublishedBozhuResultModel.GetAllPublishedBozhuRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            GetAllPublishedBozhuResultModel.view = null;
            GetAllPublishedBozhuResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
