package com.zlb.memo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vise.xsnow.event.IEvent;
import com.vise.xsnow.event.Subscribe;
import com.vondear.rxtools.RxTextTool;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.api.ResultBase;
import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.PublishPoiDraft;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.mvvm.requestModel.GetUserPublishedDraftRequestModel;
import com.zlb.memo.mvvm.viewResult.GetUserPublishedDraftResultView;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.ImageUtil;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;
import com.zlb.memo.utils.StatusHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/5.
 */

public class MyPingYouDraftActivity extends BaseActivity implements GetUserPublishedDraftResultView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ly_status)
    LinearLayout lyStatus;
    private BaseQuickAdapter<PublishPoiDraft, BaseViewHolder> adapter;

    ArrayList<PublishPoiDraft> lists = new ArrayList<>();

    private GetUserPublishedDraftRequestModel getUserPublishedDraftRequestModel = new GetUserPublishedDraftRequestModel(this);

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_pingyou_draft);
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
        SoftHideKeyBoardUtil.assistActivity(this);
    }

    @Override
    protected boolean isRegisterEvent() {
        return true;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<PublishPoiDraft, BaseViewHolder>(R.layout.item_my_pingyou_draft) {
            @Override
            protected void convert(final BaseViewHolder helper, final PublishPoiDraft item) {
                ImageView img_cover = helper.getView(R.id.img_cover);
                ImageUtil.loadImage(MyPingYouDraftActivity.this, img_cover, C.BaseImgUrl + item.getPoiList().get(0).getGdCutImage());
                TextView tv_name = helper.getView(R.id.tv_name);
                tv_name.setText(item.getPoiList().size() + "站");
                TextView tv_detail = helper.getView(R.id.tv_detail);
                TextView tv_time = helper.getView(R.id.tv_time);
                tv_time.setText(item.getPoiList().get(0).getCreationDate());
                ImageView img_delete = helper.getView(R.id.img_delete);
                img_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(mContext);//提示弹窗
                        rxDialogSureCancel.getTitleView().setText("温馨提示");
                        rxDialogSureCancel.setContent("确定删除吗？");
                        rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, String> map = new HashMap<>();
                                map.put("pingyouId", item.getPingyouId());
                                getUserPublishedDraftRequestModel.DeleteUserPublishedDraft(map);
                                lists.remove(item);
                                showIsEmpty();
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
                });
                // 响应点击事件的话必须设置以下属性
                tv_detail.setMovementMethod(LinkMovementMethod.getInstance());
                RxTextTool.Builder builder = RxTextTool.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER);
                for (int i = 0; i < item.getPoiList().size() - 1; i++) {
                    final int finalI = i;
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {

                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            ds.setColor(Color.parseColor("#DAA520"));
                            ds.setUnderlineText(false);
                        }
                    };
                    if (i == 0) {
                        builder.append("图片").setResourceId(R.drawable.normal_qidian_blue)
                                .append(item.getPoiList().get(i).getKeyword()).setClickSpan(clickableSpan)
                                .append("图片").setResourceId(R.drawable.normal_youjiantou_green);
                    } else {
                        builder.append(item.getPoiList().get(i).getKeyword()).setClickSpan(clickableSpan)
                                .append("图片").setResourceId(R.drawable.normal_youjiantou_green);
                    }
                }
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {

                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Color.parseColor("#DAA520"));
                        ds.setUnderlineText(false);
                    }
                };
                builder.append(item.getPoiList().get(item.getPoiList().size() - 1).getKeyword()).setClickSpan(clickableSpan)
                        .append("图片").setResourceId(R.drawable.normal_zhongdian_blue);
                builder.into(tv_detail);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyPingYouDraftActivity.this, PublishPingYouActivity.class);
                startActivity(intent);
            }
        });

        Map<String, String> map = new HashMap<>();
        map.put("pageNumber", String.valueOf(1));
        map.put("pageSize", "50");
        map.put("userId", C.user.getUserId());
        getUserPublishedDraftRequestModel.GetUserPublishedDraft(map);
    }

    public void showIsEmpty() {
        if (lists != null
                && lists.size() > 0) {
            adapter.replaceData(lists);
        } else {
            lists = new ArrayList<>();
            adapter.replaceData(lists);
            StatusHelper statusHelper = new StatusHelper(this, lyStatus, R.layout._loading_layout_empty);
            statusHelper.getmStatusLayoutManager().showEmptyView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe()
    public void refresh(IEvent event) {
        if (event != null && event instanceof PublishEvent) {
            if (((PublishEvent) event).getMsg().equals("OK")) {

            }
        }
    }

    @Override
    public void Fail(String msg) {

    }

    @Override
    public void Loading(String msg) {

    }

    @Override
    public void GetUserPublishedDraftSucess(BasePage<PublishPoiDraft> resultBase) {
        if (resultBase != null
                && resultBase.getList() != null) {
            lists = (ArrayList<PublishPoiDraft>) resultBase.getList();
            adapter.replaceData(lists);
        }
        showIsEmpty();
    }

    @Override
    public void DeleteUserPublishedDraftSucess(ResultBase resultBase) {

    }
}