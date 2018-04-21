package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.PublishShanghuResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishShanghuResultModel {

    public PublishShanghuResultView view;

    public PublishShanghuResultModel(PublishShanghuResultView view) {
        this.view = view;
    }

    public void PublishShanghuRequestServer(final Map<String, RequestBody> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.PublishShanghuRequestServer(map, view);
            }
        }.execute();
    }
}
