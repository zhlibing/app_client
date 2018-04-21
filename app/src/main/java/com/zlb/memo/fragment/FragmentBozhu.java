package com.zlb.memo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.vise.xsnow.event.IEvent;
import com.vise.xsnow.event.Subscribe;
import com.vondear.rxtools.view.RxToast;
import com.zlb.memo.R;
import com.zlb.memo.activity.PublishedBoZhuDetailsActivity;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshRecyclerAdapter;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshViewHolder;
import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.Note;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.fragment.base.BaseFragment;
import com.zlb.memo.mvvm.requestModel.GetAllPublishedBozhuRequestModel;
import com.zlb.memo.mvvm.viewResult.GetAllPublishedBozhuResultView;
import com.zlb.memo.utils.ImageUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentBozhu extends BaseFragment implements OnRefreshLoadmoreListener, GetAllPublishedBozhuResultView {
    @BindView(R.id.tau_header)
    WaveSwipeHeader tauHeader;
    @BindView(R.id.rc_bozhu)
    RecyclerView rcBozhu;
    @BindView(R.id.rf_refreshLayout)
    SmartRefreshLayout rfRefreshLayout;
    private BaseRefreshRecyclerAdapter<PublishBase> mAdapter;

    private GetAllPublishedBozhuRequestModel getAllPublishedBozhuRequestModel = new GetAllPublishedBozhuRequestModel(this);
    private int pageNumber = 1;
    private BasePage<PublishBase> info;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bozhu, container, false);
        ButterKnife.bind(this, view);
        rfRefreshLayout.setOnRefreshLoadmoreListener(this);
        rcBozhu.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rcBozhu.setAdapter(mAdapter = new BaseRefreshRecyclerAdapter<PublishBase>(R.layout.item_published_bozhu) {
            @Override
            protected void onBindViewHolder(BaseRefreshViewHolder holder, final PublishBase model, final int position) {
                SuperTextView tv_photo_number = holder.getView(R.id.tv_photo_number);
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_detail = holder.getView(R.id.tv_detail);
                ImageView img_cover = holder.getView(R.id.img_cover);
                tv_title.setText(model.getTitle());
                tv_detail.setText(model.getContentDetails());
                tv_photo_number.setText(model.getImagesSize());
                ImageUtil.loadImage(context, img_cover, model.getFirstImageUrl());
                img_cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PublishedBoZhuDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("URL", model.getFirstImageUrl());
                        bundle.putSerializable("note", new Note(
                                100,
                                model.getTitle(),
                                model.getContentDetails(),
                                "888",
                                model.getCreationDate()));
                        intent.putExtra("data", bundle);
                        context.startActivity(intent);
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
        getAllPublishedBozhuRequestModel.GetAllPublishedBozhu(map);
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
    public void GetAllPublishedBozhuSucess(BasePage<PublishBase> resultBase) {
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