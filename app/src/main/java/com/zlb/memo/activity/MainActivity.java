package com.zlb.memo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;

import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.adapter.viewpage.pageadapter;
import com.zlb.memo.fragment.FragmentHome;
import com.zlb.memo.fragment.FragmentNewest;
import com.zlb.memo.fragment.FragmentPersonalCenter;
import com.zlb.memo.fragment.FragmentReward;
import com.zlb.memo.fragment.test.ViewPagerUsingFragment;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.UtilsForGD;
import com.zlb.memo.widgets.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BadgeDismissListener, OnTabSelectListener {
    @Titles
    private static final String[] mTitles = {"热门", "最新", "悬赏", "我的"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.mipmap.tab1_selected, R.mipmap.tab2_selected, R.mipmap.tab3_selected, R.mipmap.tab4_selected};

    @NorIcons
    private static final int[] mNormalIcons = {R.mipmap.tab1_normal, R.mipmap.tab2_normal, R.mipmap.tab3_normal, R.mipmap.tab4_normal};
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.tabbar)
    JPTabBar tabbar;

    private List<Fragment> list = new ArrayList<>();

    private FragmentHome mTab1;

    private FragmentNewest fragmentNewest;

    private FragmentReward fragmentReward;

    private FragmentPersonalCenter fragmentPersonalCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTab1 = new FragmentHome();
        fragmentNewest = new FragmentNewest();
        fragmentReward = new FragmentReward();
        tabbar.setTabListener(this);
        fragmentPersonalCenter = new FragmentPersonalCenter();
        list.add(mTab1);
        list.add(fragmentNewest);
        list.add(fragmentReward);
        list.add(fragmentPersonalCenter);
        viewPager.setAdapter(new pageadapter(getSupportFragmentManager(), list));
        tabbar.setContainer(viewPager);
        tabbar.setDismissListener(this);
        //显示圆点模式的徽章
        //设置Badge消失的代理
        tabbar.setTabListener(this);
        tabbar.setUseScrollAnimate(true);

        initScreen();
    }

    @Override
    public void showIsEmpty() {

    }

    private void initScreen() {
        /**
         * 获取屏幕宽高
         */
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        C.screenWidth = metric.widthPixels; // 屏幕宽度（像素）
        C.screenWidth = metric.heightPixels; // 屏幕高度（像素）
    }

    @Override
    public void onDismiss(int position) {
    }


    @Override
    public void onTabSelect(int index) {
    }


    public JPTabBar getTabbar() {
        return tabbar;
    }


}
