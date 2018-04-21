package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.PublishYouhuiquanResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishYouhuiquanResultModel {

    public PublishYouhuiquanResultView view;

    public PublishYouhuiquanResultModel(PublishYouhuiquanResultView view) {
        this.view = view;
    }

    public void PublishYouhuiquanRequestServer(final Map<String, RequestBody> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.PublishYouhuiquanRequestServer(map, view);
            }
        }.execute();
    }
}
