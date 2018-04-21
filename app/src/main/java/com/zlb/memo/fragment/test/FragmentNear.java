package com.zlb.memo.fragment.test;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.zlb.memo.R;
import com.zlb.memo.adapter.TestPersonalCenterItemAdapter;
import com.zlb.memo.fragment.base.BaseFragment;
import com.zlb.memo.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class FragmentNear extends BaseFragment {

    @BindView(R.id.parallax)
    ImageView parallax;
    @BindView(R.id.attention)
    RoundTextView attention;
    @BindView(R.id.leaveword)
    RoundTextView leaveword;
    @BindView(R.id.tv_nickname)
    TextView nickname;
    @BindView(R.id.relationship_attention)
    TextView relationshipAttention;
    @BindView(R.id.relationship_fans)
    TextView relationshipFans;
    @BindView(R.id.relationship)
    LinearLayout relationship;
    @BindView(R.id.signature)
    TextView signature;
    @BindView(R.id.panel)
    RelativeLayout panel;
    @BindView(R.id.avatar)
    CircleImageView avatar;
    @BindView(R.id.visitor)
    TextView visitor;
    @BindView(R.id.panel_lyt)
    RelativeLayout panelLyt;
    @BindView(R.id.collapse)
    CollapsingToolbarLayout collapse;
    @BindView(R.id.fmc_center_dynamic)
    LinearLayout fmcCenterDynamic;
    @BindView(R.id.rc_near)
    RecyclerView rcNear;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.rf_near)
    SmartRefreshLayout rfNear;
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

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment_near, container, false);
        ButterKnife.bind(this, view);
        //状态栏透明和间距处理
        StatusBarUtil.setPaddingSmart(context, toolbar);

        rcNear.addItemDecoration(new DividerItemDecoration(context, VERTICAL));
        rcNear.setLayoutManager(new LinearLayoutManager(context));
//        rcPersonalCenter.setAdapter(new BaseRefreshRecyclerAdapter<Void>(buildData(), simple_list_item_2) {
//            @Override
//            protected void onBindViewHolder(SmartViewHolder holder, Void model, int position) {
//                holder.text(android.R.id.text1, String.format(Locale.CHINA, "第%02d条数据", position));
//                holder.text(android.R.id.text2, String.format(Locale.CHINA, "这是测试的第%02d条数据", position));
//                holder.textColorId(android.R.id.text2, R.color.red);
//            }
//        });
        TestPersonalCenterItemAdapter adapter = new TestPersonalCenterItemAdapter();
        rcNear.setAdapter(adapter);
        rcNear.setNestedScrollingEnabled(false);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("eeeee" + i);
        }
        adapter.replaceData(list);
        //添加Header
        View header = LayoutInflater.from(context).inflate(R.layout.test_fragment_personal_center_head, rcNear, false);
        adapter.addHeaderView(header);
        adapter.openLoadAnimation();
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(context, R.color.colorPrimary)&0x00ffffff;
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
        scrollView.smoothScrollTo(0,0);
        rcNear.setFocusable(false);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showIsEmpty() {

    }

    protected Collection<Void> buildData() {
        return Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }
}