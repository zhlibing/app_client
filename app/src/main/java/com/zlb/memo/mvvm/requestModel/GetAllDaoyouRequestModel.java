package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.GetAllDaoyouResultModel;
import com.zlb.memo.mvvm.viewRequest.GetAllDaoyouRequestView;
import com.zlb.memo.mvvm.viewResult.GetAllDaoyouResultView;

import java.util.Map;

/**
 */
public class GetAllDaoyouRequestModel implements GetAllDaoyouRequestView {
    private GetAllDaoyouResultModel GetAllDaoyouResultModel;

    public GetAllDaoyouRequestModel(GetAllDaoyouResultView view) {
        GetAllDaoyouResultModel = new GetAllDaoyouResultModel(view);
    }

    @Override
    public void GetAllDaoyou(Map<String, String> map) {
        GetAllDaoyouResultModel.GetAllDaoyouRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            GetAllDaoyouResultModel.view = null;
            GetAllDaoyouResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
