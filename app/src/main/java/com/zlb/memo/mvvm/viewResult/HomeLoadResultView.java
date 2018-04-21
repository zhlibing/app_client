package com.zlb.memo.mvvm.viewResult;

import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.PublishBase;

/**
 */

public interface HomeLoadResultView extends BaseResultView {
    void HomeLoadSucess(BasePage<PublishBase> user);
}
