package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.LoginResultView;

import java.util.Map;

/**
 */
public class LoginResultModel {

    public LoginResultView view;

    public LoginResultModel(LoginResultView view) {
        this.view = view;
    }

    public void loginRequestServer(final Map<Object, Object> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.loginRequestServer(map, view);
            }
        }.execute();
    }
}
