package com.zlb.memo.utils;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.zlb.memo.R;
import com.zlb.memo.ui.status.OnRetryListener;
import com.zlb.memo.ui.status.OnStatusViewListener;
import com.zlb.memo.ui.status.StatusLayoutManager;

import static com.vise.utils.handler.HandlerUtil.runOnUiThread;

/**
 * Created by Administrator on 2017/12/15.
 */

public class StatusHelper {
    private StatusLayoutManager mStatusLayoutManager;
    private Context mContext;
    private LinearLayout mLayoutMain;
    private int contentLayouyId;

    public StatusHelper(Context mContext, LinearLayout mLayoutMain, int contentLayouyId) {
        this.mContext = mContext;
        this.mLayoutMain = mLayoutMain;
        this.contentLayouyId = contentLayouyId;
        initView();
    }

    public StatusLayoutManager getmStatusLayoutManager() {
        return mStatusLayoutManager;
    }

    public void setmStatusLayoutManager(StatusLayoutManager mStatusLayoutManager) {
        this.mStatusLayoutManager = mStatusLayoutManager;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public LinearLayout getmLayoutMain() {
        return mLayoutMain;
    }

    public void setmLayoutMain(LinearLayout mLayoutMain) {
        this.mLayoutMain = mLayoutMain;
    }

    public int getContentLayouyId() {
        return contentLayouyId;
    }

    public void setContentLayouyId(int contentLayouyId) {
        this.contentLayouyId = contentLayouyId;
    }

    protected void initView() {
        mStatusLayoutManager = StatusLayoutManager.newBuilder(mContext)
                .contentView(contentLayouyId)
                .loadingView(R.layout.loading_layout_loading)
                .emptyView(R.layout.loading_layout_empty)
                .networkErrorView(R.layout.loading_layout_net_error)
                .otherErrorView(R.layout.loading_layout_other_error)
                .retryViewId(R.id.retry_button)
                .onStatusViewListener(new OnStatusViewListener() {
                    @Override
                    public void onShowView(View view, int id) {

                    }

                    @Override
                    public void onHideView(View view, int id) {

                    }
                })
                .onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        mStatusLayoutManager.showLoadingView();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mStatusLayoutManager.showContentView();
                                    }
                                });
                            }
                        }).start();
                    }
                }).build();
        mLayoutMain.addView(mStatusLayoutManager.getStatusLayout());
        mStatusLayoutManager.showLoadingView();
    }
}
