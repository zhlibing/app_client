package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.PublishPingyouResultView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 */
public class PublishPingyouResultModel {

    public PublishPingyouResultView view;

    public PublishPingyouResultModel(PublishPingyouResultView view) {
        this.view = view;
    }

    public void PublishPingyouRequestServer(final Map<String, RequestBody> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.PublishPingyouRequestServer(map, view);
            }
        }.execute();
    }
}
