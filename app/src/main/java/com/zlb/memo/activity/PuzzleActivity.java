package com.zlb.memo.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vondear.rxtools.RxImageTool;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.bean.PhotoInfo;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.StatusBarUtil;
import com.zlb.memo.widgets.puzzle.GamePintuLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登陆
 */

public class PuzzleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ly_puzzle)
    GamePintuLayout lyPuzzle;

    private PhotoInfo photoInfo;

    private WeakReference<Bitmap> bitmapWeakReference;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_puzzle);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        initView();
    }

    @Override
    public void showIsEmpty() {

    }

    private void initView() {
        showLoading("加载中");
        photoInfo = (PhotoInfo) getIntent().getSerializableExtra("MODEL");
        Message msg = new Message();
        msg.what = 0;
        mhandler.sendMessageDelayed(msg, 2000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmapWeakReference = new WeakReference<Bitmap>(RxImageTool.GetLocalOrNetBitmap(photoInfo.url));
                lyPuzzle.setmBitmap(bitmapWeakReference.get());
            }
        }).start();
    }

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    dismissLoading();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmapWeakReference.clear();
        bitmapWeakReference = null;
    }
}
