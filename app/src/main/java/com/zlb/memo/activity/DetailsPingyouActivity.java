package com.zlb.memo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.vondear.rxtools.view.likeview.RxShineButton;
import com.zhuguangmama.imagepicker.ui.ImagePagerActivity;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.bean.PhotoInfo;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.fragment.FragmentComment;
import com.zlb.memo.fragment.FragmentDaren;
import com.zlb.memo.fragment.base.SmartFragment;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;
import com.zlb.memo.widgets.MultiImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
public class DetailsPingyouActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.img_like)
    RxShineButton imgLike;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.map_map)
    TextureMapView mapMap;
    @BindView(R.id.tableLayout)
    TabLayout tableLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    private PublishBase publishBase;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private int pos = 0;

    private AMap aMap;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_details_pingyou);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        StatusBarUtil.setPaddingSmart(this, mainContent);
        initGui();
        SoftHideKeyBoardUtil.assistActivity(this);
        mapMap.onCreate(bundle);// 此方法必须重写
        aMap = mapMap.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(C.latLng, 14));
    }

    //隐藏输入框
    private void hineTypewriting() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void showIsEmpty() {

    }

    protected void initGui() {
        publishBase = (PublishBase) getIntent().getSerializableExtra("MODEL");
        fragments.add(new FragmentComment());
        fragments.add(new FragmentDaren());
        fragments.add(new SmartFragment(1));

        titles.add("评论（0）");
        titles.add("点赞（0）");
        titles.add("分享（0）");
        viewPager.setAdapter(new SmartPagerAdapter(getSupportFragmentManager()));
        tableLayout.setupWithViewPager(viewPager, true);
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
    }

    @OnClick({R.id.img_avatar, R.id.img_like})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                break;
            case R.id.img_like:
                break;
        }
    }

    private class SmartPagerAdapter extends FragmentStatePagerAdapter {

        public SmartPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titles.size();
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
}