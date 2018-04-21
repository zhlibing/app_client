package com.zlb.memo.widgets;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vondear.rxtools.view.dialog.RxDialog;
import com.zlb.memo.R;
import com.zlb.memo.utils.RxPhotoTool;

public class RxDialogChooseImage extends RxDialog {
    private TextView mTvCamera;
    private TextView mTvFile;
    private TextView mTvCancel;

    public RxDialogChooseImage(Activity context) {
        super(context);
        this.initView(context);
    }

    public RxDialogChooseImage(Fragment fragment) {
        super(fragment.getContext());
        this.initView(fragment);
    }

    public RxDialogChooseImage(Activity context, int themeResId) {
        super(context, themeResId);
        this.initView(context);
    }

    public RxDialogChooseImage(Fragment fragment, int themeResId) {
        super(fragment.getContext(), themeResId);
        this.initView(fragment);
    }

    public RxDialogChooseImage(Activity context, float alpha, int gravity) {
        super(context, alpha, gravity);
        this.initView(context);
    }

    public RxDialogChooseImage(Fragment fragment, float alpha, int gravity) {
        super(fragment.getContext(), alpha, gravity);
        this.initView(fragment);
    }

    public TextView getFromCameraView() {
        return this.mTvCamera;
    }

    public TextView getFromFileView() {
        return this.mTvFile;
    }

    public TextView getCancelView() {
        return this.mTvCancel;
    }

    private void initView(final Activity activity) {
        View dialog_view = null;
        dialog_view = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_picker_pictrue, (ViewGroup) null);

        this.mTvCamera = (TextView) dialog_view.findViewById(R.id.tv_camera);
        this.mTvFile = (TextView) dialog_view.findViewById(R.id.tv_file);
        this.mTvCancel = (TextView) dialog_view.findViewById(R.id.tv_cancel);
        this.mTvCancel.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View arg0) {
                RxDialogChooseImage.this.cancel();
            }
        });
        this.mTvCamera.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View arg0) {
                RxPhotoTool.openCameraImage(activity);
                RxDialogChooseImage.this.cancel();
            }
        });
        this.mTvFile.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View arg0) {
                RxPhotoTool.openLocalImage(activity);
                RxDialogChooseImage.this.cancel();
            }
        });
        this.setContentView(dialog_view);
        this.mLayoutParams.gravity = 80;
    }

    private void initView(final Fragment fragment) {
        View dialog_view = null;
        dialog_view = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_picker_pictrue, (ViewGroup) null);

        this.mTvCamera = (TextView) dialog_view.findViewById(R.id.tv_camera);
        this.mTvFile = (TextView) dialog_view.findViewById(R.id.tv_file);
        this.mTvCancel = (TextView) dialog_view.findViewById(R.id.tv_cancel);
        this.mTvCancel.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View arg0) {
                RxDialogChooseImage.this.cancel();
            }
        });
        this.mTvCamera.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View arg0) {
                RxPhotoTool.openCameraImage(fragment);
                RxDialogChooseImage.this.cancel();
            }
        });
        this.mTvFile.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View arg0) {
                RxPhotoTool.openLocalImage(fragment);
                RxDialogChooseImage.this.cancel();
            }
        });
        this.setContentView(dialog_view);
        this.mLayoutParams.gravity = 80;
    }
}

