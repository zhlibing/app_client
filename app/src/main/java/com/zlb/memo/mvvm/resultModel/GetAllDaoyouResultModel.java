package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.GetAllDaoyouResultView;

import java.util.Map;

/**
 */
public class GetAllDaoyouResultModel {

    public GetAllDaoyouResultView view;

    public GetAllDaoyouResultModel(GetAllDaoyouResultView view) {
        this.view = view;
    }

    public void GetAllDaoyouRequestServer(final Map<String, String> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.GetAllDaoyouRequestServer(map, view);
            }
        }.execute();
    }
}
