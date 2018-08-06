package com.zlb.memo.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.vise.xsnow.event.BusManager;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.view.dialog.RxDialogLoading;
import com.zlb.memo.App;
import com.zlb.memo.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = getClass().getSimpleName();
    protected Context mContext;
    private RxDialogLoading dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        RxActivityTool.addActivity(this);
        if (isRegisterEvent()) {
            BusManager.getBus().register(this);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEvent()) {
            BusManager.getBus().unregister(this);
        }
        try {
            this.setContentView(R.layout.empty_view_gc);
            RxActivityTool.finishActivity(this);
            dialog = null;
            mContext = null;
            System.gc();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * 子类可以重写是否注册eventbus
     */
    protected boolean isRegisterEvent() {
        return false;
    }

    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
        initToolBar(toolbar, homeAsUpEnabled, getString(resTitle));
    }

    public void showLoading(String msg) {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new RxDialogLoading(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setLoadingText(msg);
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    public abstract void showIsEmpty();
}
