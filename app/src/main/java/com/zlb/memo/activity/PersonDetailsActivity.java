package com.zlb.memo.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.vondear.rxtools.view.RxToast;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.adapter.HomeTouristAdapterRefresh;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.utils.StatusBarUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/12/12.
 */

public class PersonDetailsActivity extends BaseActivity implements OnRefreshLoadmoreListener {
    @BindView(R.id.parallax)
    ImageView parallax;
    @BindView(R.id.tv_attention)
    RoundTextView tvAttention;
    @BindView(R.id.tv_leaveword)
    RoundTextView tvLeaveword;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_relationship_attention)
    TextView tvRelationshipAttention;
    @BindView(R.id.tv_relationship_fans)
    TextView tvRelationshipFans;
    @BindView(R.id.ly_relationship)
    LinearLayout lyRelationship;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.panel)
    RelativeLayout panel;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.visitor)
    TextView visitor;
    @BindView(R.id.panel_lyt)
    RelativeLayout panelLyt;
    @BindView(R.id.collapse)
    CollapsingToolbarLayout collapse;
    @BindView(R.id.fmc_center_dynamic)
    LinearLayout fmcCenterDynamic;
    @BindView(R.id.rc_person_details)
    RecyclerView rcPersonDetails;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.rf_person_details)
    SmartRefreshLayout rfPersonDetails;
    @BindView(R.id.toolbar_avatar)
    CircleImageView toolbarAvatar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.buttonBarLayout)
    ButtonBarLayout buttonBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int mOffset = 0;
    private int mScrollY = 0;

    private HomeTouristAdapterRefresh mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_deyails);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);

        rcPersonDetails.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HomeTouristAdapterRefresh(this);
        rcPersonDetails.setAdapter(mAdapter);
        rfPersonDetails.setOnRefreshLoadmoreListener(this);
        rcPersonDetails.setNestedScrollingEnabled(false);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(PersonDetailsActivity.this, R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBarLayout.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    parallax.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });
        buttonBarLayout.setAlpha(0);
        toolbar.setBackgroundColor(0);
        scrollView.smoothScrollTo(0, 0);
        rcPersonDetails.setFocusable(false);
    }

    @Override
    public void showIsEmpty() {

    }

    @OnClick({R.id.tv_attention, R.id.tv_leaveword, R.id.tv_relationship_attention, R.id.tv_relationship_fans, R.id.img_avatar, R.id.visitor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_attention:
                break;
            case R.id.tv_leaveword:
                break;
            case R.id.tv_relationship_attention:
                break;
            case R.id.tv_relationship_fans:
                break;
            case R.id.img_avatar:
                break;
            case R.id.visitor:
                break;
        }
    }

    @Override
    public void onLoadmore(final RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.loadmore(loadModels(String.valueOf(new Random().nextInt(6))));
                refreshlayout.finishLoadmore();
                if (mAdapter.getCount() > 120) {
                    RxToast.info("数据全部加载完毕");
                    refreshlayout.setLoadmoreFinished(true);//设置之后，将不会再触发加载事件
                }
            }
        }, 1000);
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshlayout.finishRefresh();
                refreshlayout.setLoadmoreFinished(false);//恢复上拉状态
            }
        }, 2000);
    }

    /**
     * 模拟数据
     */
    private Collection<PublishBase> loadModels(String key) {
        return Arrays.asList(new PublishBase("PINGYOU")
                , new PublishBase("DAREN")
                , new PublishBase("DAOYOU")
                , new PublishBase("LVXINGZHE")
                , new PublishBase("SHANGHU")
        );
    }
}
