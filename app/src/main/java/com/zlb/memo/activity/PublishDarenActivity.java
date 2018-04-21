package com.zlb.memo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.vise.xsnow.event.BusManager;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;
import com.zhuguangmama.imagepicker.bean.ImageItem;
import com.zhuguangmama.imagepicker.widget.RYAddPictureView;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.api.ResultBase;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.mvvm.requestModel.PublishDarenRequestModel;
import com.zlb.memo.mvvm.viewResult.PublishDarenResultView;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PublishDarenActivity extends BaseActivity implements RYAddPictureView.onPickResultChangedListener, PublishDarenResultView {

    @BindView(R.id.grigView)
    RYAddPictureView grigView;
    @BindView(R.id.tv_confirm_publish)
    TextView tvConfirmPublish;
    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.ly_tags)
    TagContainerLayout lyTags;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int type;
    String title = "", content = "";

    public static final int SIZE_LIMIT = 5;//控制图片显示的个数
    public static final int REQUESTCODE = 1433;//requestCode
    private int[] delAttachInfoId = null;//删除图片的集合
    private int index = 0;
    private ArrayList<ImageItem> imageList = new ArrayList<>();//添加图片的集合

    private List<String> tags = new ArrayList<>();

    PublishDarenRequestModel publishDarenRequestModel = new PublishDarenRequestModel(this);

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_publish_daren);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hineTypewriting();
                finish();
            }
        });
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        initView();
        SoftHideKeyBoardUtil.assistActivity(this);
    }

    private void initView() {
        grigView.setOnListeners();
        grigView.setOnPickResultChangedListener(this);//删除图片后的回调监听
        grigView.setSizeLimit(SIZE_LIMIT);
        lyTags.setTags(tags);
        lyTags.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(final int position, String text) {
                final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(mContext);//提示弹窗
                rxDialogSureCancel.getTitleView().setText("温馨提示");
                rxDialogSureCancel.setContent("确定删除此标签吗？");
                rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            lyTags.removeTag(position);
                        } catch (Exception e) {

                        }
                        rxDialogSureCancel.cancel();
                    }
                });
                rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogSureCancel.cancel();
                    }
                });
                rxDialogSureCancel.show();
            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
        etKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    lyTags.removeAllViews();
                }
                try {
                    tags.clear();
                    String ss[] = s.toString().split("@");
                    for (int i = 0; i < ss.length; i++) {
                        tags.add(ss[i]);
                    }
                    lyTags.setTags(tags);
                } catch (Exception e) {

                }
            }
        });
    }


    @OnClick({R.id.tv_confirm_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_publish:
                confirmPublish();
                break;
        }
    }

    private void confirmPublish() {
        for (ImageItem imageItem : grigView.getImageList()) {
            if (!imageItem.isAdd) {
                imageList.add(imageItem);
            }
        }
        Map<String, RequestBody> files = new HashMap<>();
        for (int i = 0; i < imageList.size(); i++) {
            File file = new File(imageList.get(i).path);
            files.put("file" + i + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        files.put("userId", RequestBody.create(MediaType.parse("text/plan"), C.user.getUserId()));
        files.put("tags", RequestBody.create(MediaType.parse("text/plan"), tags.toString()));
        files.put("contentDetails", RequestBody.create(MediaType.parse("text/plan"), etContent.getText().toString()));
        publishDarenRequestModel.PublishDaren(files);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE) {
                grigView.onImageActivityResult();
            }
        }
    }

    @Override
    public void onPicDelete(ImageItem item) {
        if (item.type == ImageItem.TYPE_REMOTE && delAttachInfoId != null) {
            delAttachInfoId[index] = item.attachId;
            index++;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showIsEmpty() {

    }

    //隐藏输入框
    private void hineTypewriting() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void Fail(String msg) {
        RxToast.error(msg);
        dismissLoading();
    }

    @Override
    public void Loading(String msg) {
        showLoading(msg);
    }

    @Override
    public void PublishDarenSucess(ResultBase resultBase) {
        BusManager.getBus().post(new PublishEvent("OK"));
        dismissLoading();
        RxToast.success("恭喜发布成功！");
        finish();
    }
}