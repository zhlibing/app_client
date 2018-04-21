package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.GetAllPublishedDarenResultView;

import java.util.Map;

/**
 */
public class GetAllPublishedDarenResultModel {

    public GetAllPublishedDarenResultView view;

    public GetAllPublishedDarenResultModel(GetAllPublishedDarenResultView view) {
        this.view = view;
    }

    public void GetAllPublishedDarenRequestServer(final Map<String, String> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.GetAllPublishedDarenRequestServer(map, view);
            }
        }.execute();
    }
}
