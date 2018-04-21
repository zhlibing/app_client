package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.PublishDarenResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishDarenResultModel {

    public PublishDarenResultView view;

    public PublishDarenResultModel(PublishDarenResultView view) {
        this.view = view;
    }

    public void PublishDarenRequestServer(final Map<String, RequestBody> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.PublishDarenRequestServer(map, view);
            }
        }.execute();
    }
}
