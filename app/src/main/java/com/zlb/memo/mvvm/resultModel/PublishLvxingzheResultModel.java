package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.PublishLvxingzheResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishLvxingzheResultModel {

    public PublishLvxingzheResultView view;

    public PublishLvxingzheResultModel(PublishLvxingzheResultView view) {
        this.view = view;
    }

    public void PublishLvxingzheRequestServer(final Map<String, RequestBody> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.PublishLvxingzheRequestServer(map, view);
            }
        }.execute();
    }
}
