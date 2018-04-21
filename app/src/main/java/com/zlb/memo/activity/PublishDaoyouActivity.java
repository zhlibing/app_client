package com.zlb.memo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.vise.xsnow.event.BusManager;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.mvvm.requestModel.PublishDaoyouRequestModel;
import com.zlb.memo.mvvm.viewResult.PublishDaoyouResultView;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PublishDaoyouActivity extends BaseActivity implements PublishDaoyouResultView {

    @BindView(R.id.tv_confirm_publish)
    TextView tvConfirmPublish;
    @BindView(R.id.ly_tags)
    TagContainerLayout lyTags;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<String> tags = new ArrayList<>();
    private List<String> selectedTags = new ArrayList<>();

    private PublishDaoyouRequestModel publishDaoyouRequestModel = new PublishDaoyouRequestModel(this);

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_publish_message);
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
        tags.add("紧急");
        tags.add("有偿");
        tags.add("小知识");
        tags.add("轻松一下");
        tags.add("求助");
        tags.add("借手机");
        tags.add("充电宝");
        tags.add("小心防范");
        tags.add("失物招领");
        lyTags.setTags(tags);
        lyTags.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                if (lyTags.getTagView(position).isSelectState) {
                    selectedTags.add(text);
                    lyTags.getTagView(position).setBackgroundResource(R.drawable.corner_rectangle_green_bottom_radius_green);
                } else {
                    selectedTags.remove(text);
                    lyTags.getTagView(position).setBackgroundResource(R.color.white);
                }
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

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
        Map<String, String> map = new HashMap<>();
        map.put("contentDetails", etContent.getText().toString());
        map.put("userId", C.user.getUserId());
        map.put("tags", selectedTags.toString());
        publishDaoyouRequestModel.PublishDaoyou(map);
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

    }

    @Override
    public void Loading(String msg) {

    }

    @Override
    public void PublishDaoyouSucess(PublishBase resultBase) {
        BusManager.getBus().post(new PublishEvent("OK"));
        finish();
    }
}