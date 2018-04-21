package com.zlb.memo.mvvm.viewResult;

import com.zlb.memo.api.ResultBase;
import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.PublishPoiDraft;

/**
 */

public interface GetUserPublishedDraftResultView extends BaseResultView {
    void GetUserPublishedDraftSucess(BasePage<PublishPoiDraft> resultBase);
    void DeleteUserPublishedDraftSucess(ResultBase resultBase);
}
