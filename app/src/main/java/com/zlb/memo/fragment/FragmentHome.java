package com.zlb.memo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mmin18.widget.RealtimeBlurView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.vise.xsnow.event.IEvent;
import com.vise.xsnow.event.Subscribe;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.view.RxToast;
import com.zlb.memo.R;
import com.zlb.memo.activity.PublishBoZhuActivity;
import com.zlb.memo.activity.PublishDarenActivity;
import com.zlb.memo.activity.PublishLvXingZheActivity;
import com.zlb.memo.activity.PublishDaoyouActivity;
import com.zlb.memo.activity.PublishPingYouActivity;
import com.zlb.memo.activity.PublishShanghuActivity;
import com.zlb.memo.activity.PublishYouHuiQuanActivity;
import com.zlb.memo.adapter.HomeTouristAdapterRefresh;
import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.fragment.base.BaseFragment;
import com.zlb.memo.mvvm.requestModel.HomeLoadRequestModel;
import com.zlb.memo.mvvm.viewResult.HomeLoadResultView;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.BuilderManager;
import com.zlb.memo.utils.StatusBarUtil;
import com.zlb.memo.utils.StatusHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentHome extends BaseFragment implements OnRefreshLoadmoreListener, HomeLoadResultView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ly_status)
    LinearLayout lyStatus;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayoutHome;
    @BindView(R.id.blurview)
    RealtimeBlurView blurview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tau_header)
    TaurusHeader tauHeader;
    @BindView(R.id.bmb1)
    BoomMenuButton bmb1;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
    private HomeTouristAdapterRefresh mAdapter;

    private String key = "0";
    private static boolean isFirstEnter = true;

    HomeLoadRequestModel homeLoadRequestModel = new HomeLoadRequestModel(this);
    private int pageNumber = 1;
    private BasePage<PublishBase> info;
    private StatusHelper statusHelper;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        bmb1.setButtonEnum(ButtonEnum.TextInsideCircle);
        bmb1.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_4);
        bmb1.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_4);
        for (int i = 0; i < bmb1.getPiecePlaceEnum().pieceNumber(); i++)
            bmb1.addBuilder(BuilderManager.getTextInsideCircleButtonBuilder());
        bmb1.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                C.home_state = index;
                switch (index) {
                    case 0:
                        key = "0";
                        pageNumber = 1;
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        refreshLayoutHome.autoRefresh();
                        break;
                    case 1:
                        key = "1";
                        pageNumber = 1;
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        refreshLayoutHome.autoRefresh();
                        break;
                    case 2:
                        key = "2";
                        pageNumber = 1;
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        refreshLayoutHome.autoRefresh();
                        break;
                    case 3:
                        key = "3";
                        pageNumber = 1;
//                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        refreshLayoutHome.autoRefresh();
                        break;
                    case 4:
                        key = "4";
                        pageNumber = 1;
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        refreshLayoutHome.autoRefresh();
                        break;
                    case 5:
                        key = "5";
                        pageNumber = 1;
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        refreshLayoutHome.autoRefresh();
                        break;
                    case 6:
                        key = "6";
                        pageNumber = 1;
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        refreshLayoutHome.autoRefresh();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });
        setRegisterEvent(true);
        return view;
    }

    @Override
    protected void initData() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isHandleScroll(dy)) {
                    if (dy > 0) {//向下滑动
                        fabAdd.hide();
                    } else {//想上滑动
                        fabAdd.show();
                    }
                }
            }
        });
    }

    private void initView() {
        //第一次进入演示刷新
        if (isFirstEnter) {
            isFirstEnter = false;
            refreshLayoutHome.autoRefresh();
        }
        //初始化列表和监听
        View view = recyclerView;
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new HomeTouristAdapterRefresh(context);
            recyclerView.setAdapter(mAdapter);
            refreshLayoutHome.setOnRefreshLoadmoreListener(this);
        }
        //状态栏透明和间距处理
        StatusBarUtil.darkMode((Activity) context);
        StatusBarUtil.setPaddingSmart(context, view);
        StatusBarUtil.setPaddingSmart(context, toolbar);
        StatusBarUtil.setPaddingSmart(context, blurview);
        StatusBarUtil.setMargin(context, tauHeader);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (C.home_state) {
                    case 0:
                        RxActivityTool.skipActivity(context, PublishPingYouActivity.class);
                        break;
                    case 1:
                        RxActivityTool.skipActivity(context, PublishBoZhuActivity.class);
                        break;
                    case 2:
                        RxActivityTool.skipActivity(context, PublishDarenActivity.class);
                        break;
                    case 3:
                        RxActivityTool.skipActivity(context, PublishDaoyouActivity.class);
                        break;
                    case 4:
                        RxActivityTool.skipActivity(context, PublishLvXingZheActivity.class);
                        break;
                    case 5:
                        RxActivityTool.skipActivity(context, PublishShanghuActivity.class);
                        break;
                    case 6:
                        RxActivityTool.skipActivity(context, PublishYouHuiQuanActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
        statusHelper = new StatusHelper(getActivity(), lyStatus, R.layout.empty_view_gc);
    }

    private boolean isHandleScroll(int dy) {
        return Math.abs(dy) > 10;
    }

    private void loadModels(String key) {
        Map<Object, Object> map = new HashMap<>();
        map.put("pageNumber", String.valueOf(pageNumber));
        map.put("pageSize", "5");
        map.put("key", key);
        homeLoadRequestModel.HomeLoad(map);
    }

    @Override
    public void onLoadmore(final RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadModels(key);
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
                loadModels(key);
                refreshlayout.finishRefresh();
                refreshlayout.setLoadmoreFinished(false);//恢复上拉状态
            }
        }, 2000);
    }

    @Override
    public void Fail(String msg) {
        dismissLoading();
        showIsEmpty();
    }

    @Override
    public void Loading(String msg) {
        showLoading(msg);
    }

    @Override
    public void HomeLoadSucess(BasePage<PublishBase> info) {
        this.info = info;
        if (pageNumber == 1) {
            if (info != null
                    && info.getList() != null
                    && info.getList().size() > 0) {
                mAdapter.refresh(info.getList());
                statusHelper.getmStatusLayoutManager().showContentView();
            } else {
                showIsEmpty();
            }
        } else {
            if (info != null
                    && info.getList() != null
                    && info.getList().size() > 0) {
                mAdapter.loadmore(info.getList());
            }
        }
        pageNumber++;
        dismissLoading();
    }

    public void showIsEmpty() {
        List<PublishBase> list = new ArrayList<>();
        mAdapter.refresh(list);
        statusHelper.getmStatusLayoutManager().showEmptyView();
    }

    @Subscribe()
    public void refresh(IEvent event) {
        if (event != null && event instanceof PublishEvent) {
            if (((PublishEvent) event).getMsg().equals("OK")) {
                refreshLayoutHome.autoRefresh();
            }
        }
    }
}