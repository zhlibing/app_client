package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.GetUserPublishedDraftResultModel;
import com.zlb.memo.mvvm.viewRequest.GetUserPublishedDraftRequestView;
import com.zlb.memo.mvvm.viewResult.GetUserPublishedDraftResultView;

import java.util.Map;

/**
 */
public class GetUserPublishedDraftRequestModel implements GetUserPublishedDraftRequestView {
    private GetUserPublishedDraftResultModel GetUserPublishedDraftResultModel;

    public GetUserPublishedDraftRequestModel(GetUserPublishedDraftResultView view) {
        GetUserPublishedDraftResultModel = new GetUserPublishedDraftResultModel(view);
    }

    @Override
    public void GetUserPublishedDraft(Map<String, String> map) {
        GetUserPublishedDraftResultModel.GetUserPublishedDraftRequestServer(map);
    }

    @Override
    public void DeleteUserPublishedDraft(Map<String, String> map) {
        GetUserPublishedDraftResultModel.DeleteUserPublishedDraftRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            GetUserPublishedDraftResultModel.view = null;
            GetUserPublishedDraftResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
