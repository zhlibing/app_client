package com.zlb.memo.mvvm.resultModel;

import android.os.AsyncTask;

import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.mvvm.viewResult.GetUserPublishedDraftResultView;

import java.util.Map;

/**
 */
public class GetUserPublishedDraftResultModel {

    public GetUserPublishedDraftResultView view;

    public GetUserPublishedDraftResultModel(GetUserPublishedDraftResultView view) {
        this.view = view;
    }

    public void GetUserPublishedDraftRequestServer(final Map<String, String> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.GetUserPublishedDraftRequestServer(map, view);
            }
        }.execute();
    }

    public void DeleteUserPublishedDraftRequestServer(final Map<String, String> map) {
        new AsyncTask<Object, Integer, Object>() {
            protected Object doInBackground(Object... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                XSnowHelper.DeleteUserPublishedDraftRequestServer(map, view);
            }
        }.execute();
    }

}
