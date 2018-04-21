package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.HomeLoadResultView;

import java.util.Map;

/**
 */
public class HomeLoadResultModel {

    public HomeLoadResultView view;

    public HomeLoadResultModel(HomeLoadResultView view) {
        this.view = view;
    }

    public void HomeLoadRequestServer(final Map<Object, Object> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.HomeLoadRequestServer(map, view);
            }
        }.execute();
    }
}
