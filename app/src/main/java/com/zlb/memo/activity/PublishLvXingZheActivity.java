package com.zlb.memo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.vise.xsnow.event.BusManager;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogWheelYearMonthDay;
import com.zhuguangmama.imagepicker.bean.ImageItem;
import com.zhuguangmama.imagepicker.widget.RYAddPictureView;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.ActivityWebView;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.api.ResultBase;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.mvvm.requestModel.PublishLvxingzheRequestModel;
import com.zlb.memo.mvvm.viewResult.PublishLvxingzheResultView;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;
import com.zlb.memo.utils.UrlUtils;
import com.zlb.memo.widgets.CustomPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.listeners.OnItemPickListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PublishLvXingZheActivity extends BaseActivity implements RYAddPictureView.onPickResultChangedListener
        , PublishLvxingzheResultView {

    @BindView(R.id.grigView)
    RYAddPictureView grigView;
    @BindView(R.id.tv_confirm_publish)
    TextView tvConfirmPublish;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_weburl)
    EditText etWeburl;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_is_web)
    ImageView imgIsWeb;
    @BindView(R.id.ll_edit_self)
    LinearLayout llEditSelf;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_order_way)
    TextView tvOrderWay;
    @BindView(R.id.et_order_number)
    EditText etOrderNumber;
    @BindView(R.id.tv_weburl)
    TextView tvWeburl;
    @BindView(R.id.tv_yulan)
    SuperTextView tvYulan;
    @BindView(R.id.rl_is_web)
    RelativeLayout rlIsWeb;
    @BindView(R.id.rl_web_et)
    RelativeLayout rlWebEt;
    private int type;
    String title = "", content = "";

    public static final int SIZE_LIMIT = 5;//控制图片显示的个数
    public static final int REQUESTCODE = 1433;//requestCode
    private int[] delAttachInfoId = null;//删除图片的集合
    private int index = 0;
    private ArrayList<ImageItem> imageList = new ArrayList<>();//添加图片的集合

    private RxDialogWheelYearMonthDay mRxDialogWheelYearMonthDay;

    private PublishLvxingzheRequestModel publishLvxingzheRequestModel = new PublishLvxingzheRequestModel(this);

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_publish_lvxingzhe);
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
        initWheelYearMonthDayDialog();
    }

    private void initView() {
        grigView.setOnListeners();
        grigView.setOnPickResultChangedListener(this);//删除图片后的回调监听
        grigView.setSizeLimit(SIZE_LIMIT);

        tvYulan.setClickable(false);
        etWeburl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (UrlUtils.isURL(s.toString())) {
                    tvYulan.setVisibility(View.VISIBLE);
                    tvYulan.setClickable(true);
                } else {
                    tvYulan.setVisibility(View.GONE);
                    tvYulan.setClickable(false);
                }
            }
        });
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
        files.put("time", RequestBody.create(MediaType.parse("text/plan"), tvTime.getText().toString()));
        files.put("title", RequestBody.create(MediaType.parse("text/plan"), etTitle.getText().toString()));
        files.put("contactWay", RequestBody.create(MediaType.parse("text/plan"), tvOrderWay.getText().toString()));
        files.put("contactNumber", RequestBody.create(MediaType.parse("text/plan"), etOrderNumber.getText().toString()));
        files.put("haveOutsideLinkUrl", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(isWeb)));
        files.put("outsideLinkUrl", RequestBody.create(MediaType.parse("text/plan"), etWeburl.getText().toString()));
        files.put("contentDetails", RequestBody.create(MediaType.parse("text/plan"), etContent.getText().toString()));
        publishLvxingzheRequestModel.PublishLvxingzhe(files);
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

    int isWeb = 0;

    @OnClick({R.id.tv_confirm_publish, R.id.img_is_web, R.id.tv_time, R.id.tv_order_way, R.id.tv_weburl, R.id.tv_yulan, R.id.rl_is_web, R.id.rl_web_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_publish:
                confirmPublish();
                break;
            case R.id.img_is_web:
                if (isWeb == 1) {
                    imgIsWeb.setBackgroundResource(R.drawable.qmui_icon_checkbox_normal);
                    llEditSelf.setVisibility(View.VISIBLE);
                    rlWebEt.setVisibility(View.GONE);
                    isWeb = 0;
                } else {
                    imgIsWeb.setBackgroundResource(R.drawable.qmui_icon_checkbox_checked);
                    llEditSelf.setVisibility(View.GONE);
                    rlWebEt.setVisibility(View.VISIBLE);
                    isWeb = 1;
                }
                break;
            case R.id.tv_time:
                mRxDialogWheelYearMonthDay.show();
                break;
            case R.id.tv_order_way:
                CustomPicker picker = new CustomPicker(this);
                picker.setOffset(1);//显示的条目的偏移量，条数为（offset*2+1）
                picker.setGravity(Gravity.CENTER);//居中
                picker.setOnItemPickListener(new OnItemPickListener<String>() {
                    @Override
                    public void onItemPicked(int position, String option) {
                        tvOrderWay.setText(option);
                    }
                });
                picker.show();
                break;
            case R.id.tv_weburl:
                break;
            case R.id.tv_yulan:
                C.WEBURL = etWeburl.getText().toString();
                RxActivityTool.skipActivity(this, ActivityWebView.class);
                break;
            case R.id.rl_is_web:
                break;
            case R.id.rl_web_et:
                break;
        }
    }

    private void initWheelYearMonthDayDialog() {
        mRxDialogWheelYearMonthDay = new RxDialogWheelYearMonthDay(this, 2017, 2018);
        mRxDialogWheelYearMonthDay.getSureView().setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (mRxDialogWheelYearMonthDay.getCheckBoxDay().isChecked()) {
                            tvTime.setText(
                                    mRxDialogWheelYearMonthDay.getSelectorYear() + "年"
                                            + mRxDialogWheelYearMonthDay.getSelectorMonth() + "月"
                                            + mRxDialogWheelYearMonthDay.getSelectorDay() + "日");
                        } else {
                            tvTime.setText(
                                    mRxDialogWheelYearMonthDay.getSelectorYear() + "年"
                                            + mRxDialogWheelYearMonthDay.getSelectorMonth() + "月");
                        }
                        mRxDialogWheelYearMonthDay.cancel();
                    }
                });
        mRxDialogWheelYearMonthDay.getCancleView().setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mRxDialogWheelYearMonthDay.cancel();
                    }
                });
    }

    @Override
    public void Fail(String msg) {
        dismissLoading();
        RxToast.error(msg);
    }

    @Override
    public void Loading(String msg) {
        showLoading(msg);
    }

    @Override
    public void PublishLvxingzheSucess(ResultBase resultBase) {
        BusManager.getBus().post(new PublishEvent("OK"));
        dismissLoading();
        RxToast.success("恭喜发布成功！");
        finish();
    }
}