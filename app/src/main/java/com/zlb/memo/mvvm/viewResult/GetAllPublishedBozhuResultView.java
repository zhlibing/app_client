package com.zlb.memo.mvvm.viewResult;

import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.PublishBase;

/**
 */

public interface GetAllPublishedBozhuResultView extends BaseResultView {
    void GetAllPublishedBozhuSucess(BasePage<PublishBase> resultBase);
}
