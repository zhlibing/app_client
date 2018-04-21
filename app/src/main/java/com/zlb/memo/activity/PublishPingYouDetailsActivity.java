package com.zlb.memo.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vise.xsnow.event.BusManager;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.mvvm.requestModel.PublishPingyouRequestModel;
import com.zlb.memo.mvvm.viewResult.PublishPingyouResultView;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zlb.memo.widgets.RxDialogChooseImage;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PublishPingYouDetailsActivity extends BaseActivity implements PublishPingyouResultView {

    @BindView(R.id.tv_confirm_publish)
    TextView tvConfirmPublish;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_people_num)
    TextView tvPeopleNum;
    @BindView(R.id.tv_contact_way)
    TextView tvContactWay;
    @BindView(R.id.et_contact_way)
    EditText etContactWay;
    @BindView(R.id.tv_isfree)
    TextView tvIsfree;
    @BindView(R.id.img_wenhao_bangzhu)
    ImageView imgWenhaoBangzhu;
    @BindView(R.id.img_right_select)
    ImageView imgRightSelect;
    @BindView(R.id.img_select_cover)
    ImageView imgSelectCover;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_content)
    EditText etContent;

    private PublishPingyouRequestModel publishPingyouRequestModel = new PublishPingyouRequestModel(this);

    private String UUID;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_publish_pingyoudetails);
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
        UUID = getIntent().getExtras().getString("UUID");
    }

    @Override
    public void showIsEmpty() {

    }

    private void initView() {
    }

    //隐藏输入框
    private void hineTypewriting() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick({R.id.tv_confirm_publish, R.id.tv_time, R.id.tv_people_num, R.id.tv_contact_way, R.id.tv_isfree, R.id.img_wenhao_bangzhu, R.id.img_select_cover})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_publish:
                Map<String, RequestBody> map = new HashMap<>();
                map.put("userId", RequestBody.create(MediaType.parse("text/plan"), C.user.getUserId()));
                map.put("pingyouId", RequestBody.create(MediaType.parse("text/plan"), UUID));
                map.put("name", RequestBody.create(MediaType.parse("text/plan"), etTitle.getText().toString()));
                map.put("time", RequestBody.create(MediaType.parse("text/plan"), tvTime.getText().toString()));
                map.put("personNumber", RequestBody.create(MediaType.parse("text/plan"), tvPeopleNum.getText().toString()));
                map.put("contactWay", RequestBody.create(MediaType.parse("text/plan"), tvContactWay.getText().toString()));
                map.put("contactNumber", RequestBody.create(MediaType.parse("text/plan"), etContactWay.getText().toString()));
                map.put("isFree", RequestBody.create(MediaType.parse("text/plan"), tvIsfree.getText().toString()));
                map.put("price", RequestBody.create(MediaType.parse("text/plan"), etPrice.getText().toString()));
                map.put("attachIntroduce", RequestBody.create(MediaType.parse("text/plan"), etContent.getText().toString()));
                if(resultUri!=null){
                    File file = new File(RxPhotoTool.getImageAbsolutePath(this, resultUri));
                    if (file.exists()) {
                        map.put("file" + 0 + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    }
                }
                publishPingyouRequestModel.PublishPingyou(map);
                break;
            case R.id.tv_time:
                break;
            case R.id.tv_people_num:
                break;
            case R.id.tv_contact_way:
                break;
            case R.id.tv_isfree:
                break;
            case R.id.img_wenhao_bangzhu:
                break;
            case R.id.img_select_cover:
                initDialogChooseImage();
                break;
        }
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
    public void PublishPingyouSucess(PublishBase resultBase) {
        dismissLoading();
        RxToast.success("恭喜发布成功");
        BusManager.getBus().post(new PublishEvent("OK"));
        finish();
    }

    private void initDialogChooseImage() {
        RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(this);
        dialogChooseImage.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    initUCrop(data.getData());
                }
                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    initUCrop(RxPhotoTool.imageUriFromCamera);
                }
                break;
            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    resultUri = UCrop.getOutput(data);
                    roadImageView(resultUri, imgSelectCover);
                    RxSPTool.putContent(mContext, "AVATAR", resultUri.toString());
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //从Uri中加载图片 并将其转化成File文件返回
    private File roadImageView(Uri uri, ImageView imageView) {
        Glide.with(mContext).
                load(uri).
                diskCacheStrategy(DiskCacheStrategy.RESULT).
                thumbnail(0.5f).
                placeholder(R.drawable.normal_upload_img).
                priority(Priority.LOW).
                error(R.drawable.normal_upload_img).
                fallback(R.drawable.normal_upload_img).
                into(imageView);

        return (new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
    }

    private void initUCrop(Uri uri) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(16, 9)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(this);
    }
}