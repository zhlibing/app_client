package com.zlb.memo.fragment.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.github.mmin18.widget.RealtimeBlurView;
import com.nightonke.boommenu.BoomMenuButton;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zlb.memo.R;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshRecyclerAdapter;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshViewHolder;
import com.zlb.memo.fragment.base.BaseFragment;
import com.zlb.memo.utils.BuilderManager;
import com.zlb.memo.utils.StatusBarUtil;

import java.util.Arrays;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class FragmentHome2 extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.blurview)
    RealtimeBlurView blurview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tau_header)
    TaurusHeader tauHeader;

    private class Model {
        int imageId;
        int avatarId;
        String name;
        String nickname;
    }

    private static boolean isFirstEnter = true;
    private BaseRefreshRecyclerAdapter<Model> mAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment_home2, container, false);
        ButterKnife.bind(this, view);
        initView();
        BoomMenuButton bmb1 = (BoomMenuButton) view.findViewById(R.id.bmb1);
        for (int i = 0; i < bmb1.getPiecePlaceEnum().pieceNumber(); i++)
            bmb1.addBuilder(BuilderManager.getSquareSimpleCircleButtonBuilder());
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showIsEmpty() {

    }

    private void initView() {
        //第一次进入演示刷新
        if (isFirstEnter) {
            isFirstEnter = false;
            refreshLayout.autoRefresh();
        }

        //初始化列表和监听
        View view = recyclerView;
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new BaseRefreshRecyclerAdapter<Model>(loadModels(), R.layout.item_home_guide) {
                @Override
                protected void onBindViewHolder(BaseRefreshViewHolder holder, Model model, int position) {
                }
            };
            SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
            alphaAdapter.setFirstOnly(false);
            alphaAdapter.setDuration(1000);
            alphaAdapter.setInterpolator(new OvershootInterpolator(1.0f));
            recyclerView.setAdapter(alphaAdapter);

            refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
                @Override
                public void onRefresh(final RefreshLayout refreshlayout) {
                    refreshLayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshlayout.finishRefresh();
                            refreshlayout.setLoadmoreFinished(false);//恢复上拉状态
                        }
                    }, 2000);
                }

                @Override
                public void onLoadmore(final RefreshLayout refreshlayout) {
                    refreshLayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.loadmore(loadModels());
                            refreshlayout.finishLoadmore();
                            if (mAdapter.getCount() > 12) {
                                Toast.makeText(context, "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                                refreshlayout.setLoadmoreFinished(true);//设置之后，将不会再触发加载事件
                            }
                        }
                    }, 1000);
                }
            });
        }

        //状态栏透明和间距处理
        StatusBarUtil.darkMode((Activity) context);
        StatusBarUtil.setPaddingSmart((Activity) context, view);
        StatusBarUtil.setPaddingSmart((Activity) context, toolbar);
        StatusBarUtil.setPaddingSmart((Activity) context, blurview);
        StatusBarUtil.setMargin((Activity) context, tauHeader);
    }

    /**
     * 模拟数据
     */
    private Collection<Model> loadModels() {
        return Arrays.asList(
                new Model() {{
                    this.name = "但家香酥鸭";
                    this.nickname = "爱过那张脸";
                    this.imageId = R.mipmap.circle;
                    this.avatarId = R.mipmap.circle;
                }}, new Model() {{
                    this.name = "香菇蒸鸟蛋";
                    this.nickname = "淑女算个鸟";
                    this.imageId = R.mipmap.circle;
                    this.avatarId = R.mipmap.circle;
                }}, new Model() {{
                    this.name = "花溪牛肉粉";
                    this.nickname = "性感妩媚";
                    this.imageId = R.mipmap.circle;
                    this.avatarId = R.mipmap.circle;
                }}, new Model() {{
                    this.name = "破酥包";
                    this.nickname = "一丝丝纯真";
                    this.imageId = R.mipmap.circle;
                    this.avatarId = R.mipmap.circle;
                }}, new Model() {{
                    this.name = "盐菜饭";
                    this.nickname = "等着你回来";
                    this.imageId = R.mipmap.circle;
                    this.avatarId = R.mipmap.circle;
                }}, new Model() {{
                    this.name = "米豆腐";
                    this.nickname = "宝宝树人";
                    this.imageId = R.mipmap.circle;
                    this.avatarId = R.mipmap.circle;
                }});
    }

}