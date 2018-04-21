package com.zlb.memo.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RelativeLayout;

import com.jaredrummler.android.widget.AnimatedSvgView;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.RxBarTool;
import com.vondear.rxtools.RxPermissionsTool;
import com.vondear.rxtools.activity.ActivityBase;
import com.zlb.memo.R;
import com.zlb.memo.bean.ModelSVG;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends ActivityBase {

    @BindView(R.id.animated_svg_view)
    AnimatedSvgView mSvgView;
    @BindView(R.id.activity_svg)
    RelativeLayout mActivitySvg;
    private Handler checkhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBarTool.hideStatusBar(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setSvg(ModelSVG.values()[9]);
        CheckUpdate();
        initPermion();
    }

    private void initPermion() {
        RxPermissionsTool.
                with(mContext).
                addPermission(Manifest.permission.ACCESS_FINE_LOCATION).
                addPermission(Manifest.permission.ACCESS_COARSE_LOCATION).
                addPermission(Manifest.permission.READ_EXTERNAL_STORAGE).
                addPermission(Manifest.permission.CAMERA).
                addPermission(Manifest.permission.CALL_PHONE).
                addPermission(Manifest.permission.READ_PHONE_STATE).
                initPermission();
    }

    private void setSvg(ModelSVG modelSvg) {
        mSvgView.setGlyphStrings(modelSvg.glyphs);
        mSvgView.setFillColors(modelSvg.colors);
        mSvgView.setViewportSize(modelSvg.width, modelSvg.height);
        mSvgView.setTraceResidueColor(0x32000000);
        mSvgView.setTraceColors(modelSvg.colors);
        mSvgView.rebuildGlyphData();
        mSvgView.start();
    }

    /**
     * 检查是否有新版本，如果有就升级
     */
    private void CheckUpdate() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(500);
                    Message msg = checkhandler.obtainMessage();
                    checkhandler.sendMessage(msg);
                    Thread.sleep(3000);
                    toMain();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void toMain() {
        RxActivityTool.skipActivityAndFinish(this, LoginActivity.class);
    }
}
