package com.zlb.memo.fragment.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vondear.rxtools.RxActivityTool;
import com.zhuguangmama.imagepicker.ui.ImagePagerActivity;
import com.zlb.memo.R;
import com.zlb.memo.activity.DetailsDarenActivity;
import com.zlb.memo.activity.PuzzleActivity;
import com.zlb.memo.adapter.HomeTravelsPhotoItemAdapter;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshRecyclerAdapter;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshViewHolder;
import com.zlb.memo.bean.PhotoInfo;
import com.zlb.memo.bean.PublishBozhu;
import com.zlb.memo.utils.DatasUtil;
import com.zlb.memo.widgets.MultiImageViewForStaggeredGrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

@SuppressLint("ValidFragment")
public class SmartFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private BaseRefreshRecyclerAdapter<Void> mAdapter;
    private int position;

    @SuppressLint("ValidFragment")
    public SmartFragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(inflater.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        switch (position) {
            case 0:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(mAdapter = new BaseRefreshRecyclerAdapter<Void>(initData(), R.layout.item_home_travels) {
                    @Override
                    protected void onBindViewHolder(BaseRefreshViewHolder holder, Void model, int position) {
                        RecyclerView rc_members = holder.getView(R.id.rc_photo);
                        rc_members.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        HomeTravelsPhotoItemAdapter personalCenterItemAdapter = new HomeTravelsPhotoItemAdapter(getActivity());
                        rc_members.setAdapter(personalCenterItemAdapter);
                        List<PublishBozhu> list = new ArrayList<>();
                        for (int i = 0; i < 9; i++) {
                            list.add(new PublishBozhu());
                        }
                        personalCenterItemAdapter.replaceData(list);
                    }
                });
                break;
            case 1:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(mAdapter = new BaseRefreshRecyclerAdapter<Void>(initData(), R.layout.item_home_news_2) {
                    @Override
                    protected void onBindViewHolder(BaseRefreshViewHolder holder, Void model, int position) {
                        MultiImageViewForStaggeredGrid multiImagView = holder.getView(R.id.multiImagView);
                        final List<PhotoInfo> photoInfos = DatasUtil.createPhotos();
                        multiImagView.setList(photoInfos);
                        multiImagView.setOnItemClickListener(new MultiImageViewForStaggeredGrid.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
//                                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
//                                List<String> photoUrls = new ArrayList<String>();
//                                for (PhotoInfo photoInfo : photoInfos) {
//                                    photoUrls.add(photoInfo.url);
//                                }
//                                ImagePagerActivity.startImagePagerActivity(getActivity(), photoUrls, position, imageSize);
                                Bundle b = new Bundle();
                                b.putSerializable("MODEL", photoInfos.get(position));
                                RxActivityTool.skipActivity(getActivity(), PuzzleActivity.class, b);
                            }
                        });
                    }
                });
                break;
            case 2:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mAdapter = new BaseRefreshRecyclerAdapter<Void>(initData(), R.layout.item_home_guide) {
                    @Override
                    protected void onBindViewHolder(BaseRefreshViewHolder holder, Void model, int position) {
                    }
                });
                break;
            default:
                break;
        }
    }

    private Collection<Void> initData() {
        return Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public void onRefresh(final RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.refresh(initData());
                refreshlayout.finishRefresh();
                refreshlayout.resetNoMoreData();
            }
        }, 2000);
    }

    public void onLoadmore(final RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.loadmore(initData());
                if (mAdapter.getItemCount() > 60) {
                    Toast.makeText(getContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                    refreshlayout.finishLoadmoreWithNoMoreData();//将不会再次触发加载更多事件
                } else {
                    refreshlayout.finishLoadmore();
                }
            }
        }, 2000);
    }
}