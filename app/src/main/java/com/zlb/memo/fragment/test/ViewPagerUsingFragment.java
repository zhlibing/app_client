package com.zlb.memo.fragment.test;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.github.mmin18.widget.RealtimeBlurView;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.ActivityWebView;
import com.zlb.memo.adapter.ServiceFragmentAdvHolder;
import com.zlb.memo.bean.TestModel;
import com.zlb.memo.fragment.base.BaseFragment;
import com.zlb.memo.fragment.base.SmartFragment;
import com.zlb.memo.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 使用示例-ViewPager页面
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerUsingFragment extends BaseFragment implements OnRefreshLoadmoreListener {

    @BindView(R.id.convenient)
    ConvenientBanner convenient;
    @BindView(R.id.tableLayout)
    TabLayout tableLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.rf_refreshLayout)
    SmartRefreshLayout rfRefreshLayout;
    @BindView(R.id.blurview)
    RealtimeBlurView blurview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tau_header)
    WaveSwipeHeader tauHeader;

    public enum Item {
        NestedDav("大V", SmartFragment.class),
        NestedDaren("达人", SmartFragment.class),
        NestedGuanzhu("关注", SmartFragment.class),;
        public String name;
        public Class<? extends Fragment> clazz;

        Item(String name, Class<? extends Fragment> clazz) {
            this.name = name;
            this.clazz = clazz;
        }
    }

    private SmartPagerAdapter mAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_using_viewpager, container, false);
        ButterKnife.bind(this, view);

        viewPager.setAdapter(mAdapter = new SmartPagerAdapter(Item.values()));
        tableLayout.setupWithViewPager(viewPager, true);

        StatusBarUtil.setPaddingSmart(context, blurview);
        StatusBarUtil.setPaddingSmart(context, mainContent);
        StatusBarUtil.setPaddingSmart(context, toolbar);
        StatusBarUtil.setMargin(context, tauHeader);

        return view;
    }

    @Override
    protected void initData() {
        rfRefreshLayout.setOnRefreshLoadmoreListener(this);
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
    }

    @Override
    public void showIsEmpty() {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mAdapter.fragments[viewPager.getCurrentItem()].onRefresh(refreshlayout);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mAdapter.fragments[viewPager.getCurrentItem()].onLoadmore(refreshlayout);
    }

    private class SmartPagerAdapter extends FragmentStatePagerAdapter {

        private final Item[] items;
        private final SmartFragment[] fragments;

        SmartPagerAdapter(Item... items) {
            super(getChildFragmentManager());
            this.items = items;
            this.fragments = new SmartFragment[items.length];
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items[position].name;
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments[position] == null) {
                fragments[position] = new SmartFragment(position);
            }
            return fragments[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
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
}
