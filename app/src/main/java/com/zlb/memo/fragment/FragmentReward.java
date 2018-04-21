package com.zlb.memo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.vise.xsnow.event.IEvent;
import com.vise.xsnow.event.Subscribe;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.view.RxToast;
import com.zlb.memo.R;
import com.zlb.memo.activity.PersonDetailsActivity;
import com.zlb.memo.activity.base.ActivityWebView;
import com.zlb.memo.adapter.CareUserItemAdapter;
import com.zlb.memo.adapter.RewardFragmentAdapterRefresh;
import com.zlb.memo.adapter.ServiceFragmentAdvHolder;
import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.bean.TestModel;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.fragment.base.BaseFragment;
import com.zlb.memo.mvvm.requestModel.GetAllDaoyouRequestModel;
import com.zlb.memo.mvvm.viewResult.GetAllDaoyouResultView;
import com.zlb.memo.utils.StatusHelper;
import com.zlb.memo.widgets.RecyclerViewDisabler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentReward extends BaseFragment implements OnRefreshLoadmoreListener, GetAllDaoyouResultView {

    @BindView(R.id.rc_hot_user)
    RecyclerView rcHotUser;
    @BindView(R.id.rc_hot)
    RecyclerView rcHot;
    @BindView(R.id.rf_hot)
    SmartRefreshLayout rfHot;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.ly_title)
    LinearLayout lyTitle;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorlayout;
    @BindView(R.id.fab_menu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.tau_header)
    WaveSwipeHeader tauHeader;
    @BindView(R.id.convenient)
    ConvenientBanner convenient;
    @BindView(R.id.menu_item_collect)
    FloatingActionButton menuItemCollect;
    @BindView(R.id.menu_item_comment)
    FloatingActionButton menuItemComment;

    RewardFragmentAdapterRefresh rewardFragmentAdapter;

    private GetAllDaoyouRequestModel getAllDaoyouRequestModel = new GetAllDaoyouRequestModel(this);
    private int pageNumber = 1;
    private BasePage<PublishBase> info;

    private static boolean isFirstEnter = true;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reward, container, false);
        ButterKnife.bind(this, view);
        rcHot.setLayoutManager(new LinearLayoutManager(context));
        rewardFragmentAdapter = new RewardFragmentAdapterRefresh(R.layout.item_reward_fragmen);
        rcHot.setAdapter(rewardFragmentAdapter);

        CareUserItemAdapter adapter2 = new CareUserItemAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rcHotUser.setLayoutManager(linearLayoutManager);
        rcHotUser.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RxActivityTool.skipActivity(getActivity(), PersonDetailsActivity.class);
            }
        });
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("eeeee" + i);
        }
        adapter2.replaceData(list);
        rfHot.setOnRefreshLoadmoreListener(this);
        setRegisterEvent(true);
        return view;
    }

    @Override
    protected void initData() {
        final RecyclerViewDisabler mRecyclerViewDisabler = new RecyclerViewDisabler();

        fabMenu.setClosedOnTouchOutside(true);
        fabMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    rcHot.addOnItemTouchListener(mRecyclerViewDisabler);
                } else {
                    rcHot.removeOnItemTouchListener(mRecyclerViewDisabler);
                }
            }
        });
        rcHot.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isHandleScroll(dy)) {
                    // 表示是否打开菜单
                    boolean opened = fabMenu.isOpened();
                    //   表示菜单Menu是否隐藏
                    boolean menuHidden = fabMenu.isMenuHidden();
                    //  表示菜单menu Item是否隐藏
                    boolean menuButtonHidden = fabMenu.isMenuButtonHidden();
                    if (dy > 0) {//向下滑动
                        if (menuHidden == false) {
                            fabMenu.hideMenu(true);
                        }
                    } else {//想上滑动
                        if (menuHidden == true) {
                            fabMenu.showMenu(true);
                        }
                    }
                }
            }
        });

        final List<TestModel> adverInfoList = new ArrayList<>();
        adverInfoList.add(new TestModel("http://v1.qzone.cc/avatar/201308/30/22/56/5220b2828a477072.jpg%21200x200.jpg"));
        adverInfoList.add(new TestModel("http://v1.qzone.cc/avatar/201308/22/10/36/521579394f4bb419.jpg!200x200.jpg"));
        adverInfoList.add(new TestModel("http://v1.qzone.cc/avatar/201408/20/17/23/53f468ff9c337550.jpg!200x200.jpg"));
        if (adverInfoList.size() >= 1) {
            convenient.setPages(new CBViewHolderCreator() {
                @Override
                public ServiceFragmentAdvHolder createHolder() {
                    return new ServiceFragmentAdvHolder();
                }
            }, adverInfoList)
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            if (adverInfoList.get(position).getA() != null
                                    && adverInfoList.get(position).getA().length() > 0
                                    && !adverInfoList.get(position).getA().equals("null")) {
                                Intent intent = new Intent(getActivity(), ActivityWebView.class);
                                getActivity().startActivity(intent);
                            }
                        }
                    })
                    .setPageIndicator(new int[]{R.drawable.indicator_selected_shape,
                            R.drawable.indicator_unselected_shape});
        } else {
            convenient.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.default_img));
        }
        //第一次进入演示刷新
        if (isFirstEnter) {
            isFirstEnter = false;
            rfHot.autoRefresh();
        }
    }

    private boolean isHandleScroll(int dy) {
        return Math.abs(dy) > 10;
    }

    @Override
    public void onLoadmore(final RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadModels();
                refreshlayout.finishLoadmore();
                if (info != null
                        && !info.hasNextPage()) {
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
                pageNumber = 1;
                loadModels();
                refreshlayout.finishRefresh();
                refreshlayout.setLoadmoreFinished(false);//恢复上拉状态
            }
        }, 2000);
    }

    private void loadModels() {
        Map<String, String> map = new HashMap<>();
        map.put("pageNumber", String.valueOf(pageNumber));
        map.put("pageSize", "5");
        getAllDaoyouRequestModel.GetAllDaoyou(map);
    }

    @OnClick({R.id.menu_item_collect, R.id.menu_item_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_item_collect:
                break;
            case R.id.menu_item_comment:
                break;
        }
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        convenient.startTurning(3000);
    }

    //停止翻页
    @Override
    public void onPause() {
        super.onPause();
        convenient.stopTurning();
    }

    @Override
    public void Fail(String msg) {
        dismissLoading();
    }

    @Override
    public void Loading(String msg) {
        showLoading(msg);
    }

    @Override
    public void GetAllDaoyouSucess(BasePage<PublishBase> resultBase) {
        this.info = resultBase;
        if (pageNumber == 1) {
            if (info != null
                    && info.getList() != null
                    && info.getList().size() > 0) {
                rewardFragmentAdapter.refresh(info.getList());
            } else {
                showIsEmpty();
            }
        } else {
            if (info != null
                    && info.getList() != null
                    && info.getList().size() > 0) {
                rewardFragmentAdapter.loadmore(info.getList());
            }
        }
        pageNumber++;
        dismissLoading();
    }

    public void showIsEmpty() {
    }

    @Subscribe()
    public void refresh(IEvent event) {
        if (event != null && event instanceof PublishEvent) {
            if (((PublishEvent) event).getMsg().equals("OK")){
                rfHot.autoRefresh();
            }
        }
    }
}