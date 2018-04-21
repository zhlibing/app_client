package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.RegistResultView;

import java.util.Map;

/**
 */
public class RegistResultModel {

    public RegistResultView view;

    public RegistResultModel(RegistResultView view) {
        this.view = view;
    }

    public void RegistRequestServer(final Map<Object, Object> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.RegistRequestServer(map, view);
            }
        }.execute();
    }
}
