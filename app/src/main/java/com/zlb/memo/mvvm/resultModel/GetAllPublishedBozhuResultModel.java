package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.GetAllPublishedBozhuResultView;

import java.util.Map;

/**
 */
public class GetAllPublishedBozhuResultModel {

    public GetAllPublishedBozhuResultView view;

    public GetAllPublishedBozhuResultModel(GetAllPublishedBozhuResultView view) {
        this.view = view;
    }

    public void GetAllPublishedBozhuRequestServer(final Map<String, String> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.GetAllPublishedBozhuRequestServer(map, view);
            }
        }.execute();
    }
}
