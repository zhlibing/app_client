package com.zlb.memo.mvvm.requestModel;

import com.zlb.memo.mvvm.resultModel.LoginResultModel;
import com.zlb.memo.mvvm.viewResult.LoginResultView;
import com.zlb.memo.mvvm.viewRequest.LoginRequestView;

import java.util.Map;

/**
 */
public class LoginRequestModel implements LoginRequestView {
    private LoginResultModel loginResultModel;

    public LoginRequestModel(LoginResultView view) {
        loginResultModel = new LoginResultModel(view);
    }

    @Override
    public void Login(Map<Object, Object> map) {
        loginResultModel.loginRequestServer(map);
    }

    @Override
    public void Recycle() {
        try {
            loginResultModel.view = null;
            loginResultModel = null;
            System.gc();
        } catch (Exception e) {
        }
    }
}
