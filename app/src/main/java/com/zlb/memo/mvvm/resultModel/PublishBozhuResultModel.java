package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.PublishBozhuResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishBozhuResultModel {

    public PublishBozhuResultView view;

    public PublishBozhuResultModel(PublishBozhuResultView view) {
        this.view = view;
    }

    public void PublishBozhuRequestServer(final Map<String, String> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.PublishBozhuRequestServer(map, view);
            }
        }.execute();
    }
}
