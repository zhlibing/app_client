package com.zlb.memo.mvvm.viewResult;

import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.PublishBase;

/**
 */

public interface GetAllPublishedDarenResultView extends BaseResultView {
    void GetAllPublishedDarenSucess(BasePage<PublishBase> resultBase);
}
