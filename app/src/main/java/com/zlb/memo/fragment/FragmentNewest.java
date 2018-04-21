package com.zlb.memo.fragment;


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

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.github.mmin18.widget.RealtimeBlurView;
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
public class FragmentNewest extends BaseFragment {

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
    @BindView(R.id.blurview)
    RealtimeBlurView blurview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private int pos = 0;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newest, container, false);
        ButterKnife.bind(this, view);

        fragments.add(new FragmentBozhu());
        fragments.add(new FragmentDaren());
        fragments.add(new SmartFragment(1));
        titles.add("精选文章");
        titles.add("达人心情");
        titles.add("关注动态");
        viewPager.setAdapter(new SmartPagerAdapter());
        tableLayout.setupWithViewPager(viewPager, true);

        StatusBarUtil.setPaddingSmart(context, blurview);
        StatusBarUtil.setPaddingSmart(context, mainContent);
        StatusBarUtil.setPaddingSmart(context, toolbar);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    protected void initData() {
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

    private class SmartPagerAdapter extends FragmentStatePagerAdapter {

        SmartPagerAdapter() {
            super(getChildFragmentManager());
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments.get(position) == null) {
                return new Fragment();
            }
            return fragments.get(position);
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
