package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.RegistResultModel;
import com.zlb.memo.mvvm.viewRequest.RegistRequestView;
import com.zlb.memo.mvvm.viewResult.RegistResultView;

import java.util.Map;

/**
 */
public class RegistRequestModel implements RegistRequestView {
    private RegistResultModel RegistResultModel;

    public RegistRequestModel(RegistResultView view) {
        RegistResultModel = new RegistResultModel(view);
    }

    @Override
    public void Regist(Map<Object, Object> map) {
        RegistResultModel.RegistRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            RegistResultModel.view = null;
            RegistResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
