package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.PublishDaoyouResultView;

import java.util.Map;

/**
 */
public class PublishDaoyouResultModel {

    public PublishDaoyouResultView view;

    public PublishDaoyouResultModel(PublishDaoyouResultView view) {
        this.view = view;
    }

    public void PublishDaoyouRequestServer(final Map<String, String> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.PublishDaoyouRequestServer(map, view);
            }
        }.execute();
    }
}
