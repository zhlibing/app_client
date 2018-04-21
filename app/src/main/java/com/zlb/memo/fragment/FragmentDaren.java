package com.zlb.memo.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.vise.xsnow.event.IEvent;
import com.vise.xsnow.event.Subscribe;
import com.vondear.rxtools.view.RxToast;
import com.zhuguangmama.imagepicker.ui.ImagePagerActivity;
import com.zlb.memo.R;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshRecyclerAdapter;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshViewHolder;
import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.PhotoInfo;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.fragment.base.BaseFragment;
import com.zlb.memo.mvvm.requestModel.GetAllPublishedDarenRequestModel;
import com.zlb.memo.mvvm.viewResult.GetAllPublishedDarenResultView;
import com.zlb.memo.overall.C;
import com.zlb.memo.widgets.MultiImageViewForStaggeredGrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentDaren extends BaseFragment implements OnRefreshLoadmoreListener, GetAllPublishedDarenResultView {
    @BindView(R.id.tau_header)
    WaveSwipeHeader tauHeader;
    @BindView(R.id.rc_bozhu)
    RecyclerView rcBozhu;
    @BindView(R.id.rf_refreshLayout)
    SmartRefreshLayout rfRefreshLayout;
    private BaseRefreshRecyclerAdapter<PublishBase> mAdapter;

    private GetAllPublishedDarenRequestModel getAllPublishedDarenRequestModel = new GetAllPublishedDarenRequestModel(this);
    private int pageNumber = 1;
    private BasePage<PublishBase> info;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bozhu, container, false);
        ButterKnife.bind(this, view);
        rfRefreshLayout.setOnRefreshLoadmoreListener(this);
        rcBozhu.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rcBozhu.setAdapter(mAdapter = new BaseRefreshRecyclerAdapter<PublishBase>(R.layout.item_home_news_2) {
            @Override
            protected void onBindViewHolder(BaseRefreshViewHolder holder, PublishBase model, int position) {
                TextView tv_content = holder.getView(R.id.tv_content);
                tv_content.setText(model.getContentDetails());
                MultiImageViewForStaggeredGrid multiImagView = holder.getView(R.id.multiImagView);
                final List<PhotoInfo> photoInfos = new ArrayList<>();
                for (int i = 0; i < model.getImages().size(); i++) {
                    photoInfos.add(new PhotoInfo(C.BaseImgUrl + model.getImages().get(i).getImageUrl()));
                }
                multiImagView.setList(photoInfos);
                multiImagView.setOnItemClickListener(new MultiImageViewForStaggeredGrid.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                        List<String> photoUrls = new ArrayList<String>();
                        for (PhotoInfo photoInfo : photoInfos) {
                            photoUrls.add(photoInfo.url);
                        }
                        ImagePagerActivity.startImagePagerActivity(context, photoUrls, position, imageSize);
                    }
                });
            }
        });
        setRegisterEvent(true);
        return view;
    }

    @Override
    protected void initData() {
        loadModels();
    }

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
        getAllPublishedDarenRequestModel.GetAllPublishedDaren(map);
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
    public void GetAllPublishedDarenSucess(BasePage<PublishBase> resultBase) {
        this.info = resultBase;
        if (pageNumber == 1) {
            if (info != null
                    && info.getList() != null
                    && info.getList().size() > 0) {
                mAdapter.refresh(info.getList());
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
    }

    @Subscribe()
    public void refresh(IEvent event) {
        if (event != null && event instanceof PublishEvent) {
            if (((PublishEvent) event).getMsg().equals("OK")) {
                rfRefreshLayout.autoRefresh();
            }
        }
    }
}