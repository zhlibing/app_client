package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.GetAllPublishedDarenResultModel;
import com.zlb.memo.mvvm.viewRequest.GetAllPublishedDarenRequestView;
import com.zlb.memo.mvvm.viewResult.GetAllPublishedDarenResultView;

import java.util.Map;

/**
 */
public class GetAllPublishedDarenRequestModel implements GetAllPublishedDarenRequestView {
    private GetAllPublishedDarenResultModel GetAllPublishedDarenResultModel;

    public GetAllPublishedDarenRequestModel(GetAllPublishedDarenResultView view) {
        GetAllPublishedDarenResultModel = new GetAllPublishedDarenResultModel(view);
    }

    @Override
    public void GetAllPublishedDaren(Map<String, String> map) {
        GetAllPublishedDarenResultModel.GetAllPublishedDarenRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            GetAllPublishedDarenResultModel.view = null;
            GetAllPublishedDarenResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
