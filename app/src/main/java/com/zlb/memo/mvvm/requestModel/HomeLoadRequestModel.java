package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.HomeLoadResultModel;
import com.zlb.memo.mvvm.viewRequest.HomeLoadRequestView;
import com.zlb.memo.mvvm.viewResult.HomeLoadResultView;

import java.util.Map;

/**
 */
public class HomeLoadRequestModel implements HomeLoadRequestView {
    private HomeLoadResultModel HomeLoadResultModel;

    public HomeLoadRequestModel(HomeLoadResultView view) {
        HomeLoadResultModel = new HomeLoadResultModel(view);
    }

    @Override
    public void HomeLoad(Map<Object, Object> map) {
        HomeLoadResultModel.HomeLoadRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            HomeLoadResultModel.view = null;
            HomeLoadResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
