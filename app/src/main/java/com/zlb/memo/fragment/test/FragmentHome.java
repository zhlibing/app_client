package com.zlb.memo.fragment.test;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stone.card.library.CardAdapter;
import com.stone.card.library.CardSlidePanel;
import com.vondear.rxtools.view.likeview.RxShineButton;
import com.zlb.memo.R;
import com.zlb.memo.adapter.TestPersonalCenterItemAdapter;
import com.zlb.memo.fragment.base.BaseFragment;
import com.zlb.memo.bean.CardDataItem;
import com.zlb.memo.utils.StatusBarUtil;
import com.zys.brokenview.BrokenCallback;
import com.zys.brokenview.BrokenTouchListener;
import com.zys.brokenview.BrokenView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class FragmentHome extends BaseFragment {

    @BindView(R.id.notify_change)
    ImageView notifyChange;
    @BindView(R.id.image_slide_panel)
    CardSlidePanel imageSlidePanel;
    @BindView(R.id.rl_unlike)
    RelativeLayout rlUnlike;
    @BindView(R.id.rl_details)
    RelativeLayout rlDetails;
    @BindView(R.id.rl_like)
    RelativeLayout rlLike;
    @BindView(R.id.po_image2)
    RxShineButton poImage2;
    @BindView(R.id.po_image3)
    RxShineButton poImage3;
    @BindView(R.id.rc_home_rm)
    RecyclerCoverFlow rc_home_rm;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CardSlidePanel.CardSwitchListener cardSwitchListener;

    private String imagePaths[] = {"file:///android_asset/wall01.jpg",
            "file:///android_asset/wall02.jpg", "file:///android_asset/wall03.jpg",
            "file:///android_asset/wall04.jpg", "file:///android_asset/wall05.jpg",
            "file:///android_asset/wall06.jpg", "file:///android_asset/wall07.jpg",
            "file:///android_asset/wall08.jpg", "file:///android_asset/wall09.jpg",
            "file:///android_asset/wall10.jpg", "file:///android_asset/wall11.jpg",
            "file:///android_asset/wall12.jpg"}; // 12个图片资源

    private String names[] = {"郭富城", "刘德华", "张学友", "李连杰", "成龙", "谢霆锋", "李易峰",
            "霍建华", "胡歌", "曾志伟", "吴孟达", "梁朝伟"}; // 12个人名

    private List<CardDataItem> dataList = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment_home, container, false);
        ButterKnife.bind(this, view);
        StatusBarUtil.setPaddingSmart(getContext(), view.findViewById(R.id.toolbar));
        initView();
        final BrokenView brokenView = BrokenView.add2Window((Activity) context);
        BrokenTouchListener listener = new BrokenTouchListener.Builder(brokenView).build();
        view.setOnTouchListener(listener);
        brokenView.setCallback(new BrokenCallback() {
            @Override
            public void onStart(View v) {
            }

            @Override
            public void onCancel(View v) {
            }

            @Override
            public void onCancelEnd(View v) {
                brokenView.reset();
            }

            @Override
            public void onRestart(View v) {
            }

            @Override
            public void onFalling(View v) {
            }

            @Override
            public void onFallingEnd(View v) {
                brokenView.reset();
            }
        });
        poImage2.init((Activity) context);
        poImage3.init((Activity) context);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showIsEmpty() {

    }

    private void initView() {
        // 1. 左右滑动监听
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
                Log.d("Card", "正在显示-" + dataList.get(index).userName);
            }

            @Override
            public void onCardVanish(int index, int type) {
                Log.d("Card", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);
            }
        };
        imageSlidePanel.setCardSwitchListener(cardSwitchListener);


        // 2. 绑定Adapter
        imageSlidePanel.setAdapter(new CardAdapter() {
            @Override
            public int getLayoutId() {
                return R.layout.card_item;
            }

            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public void bindView(View view, int index) {
                Object tag = view.getTag();
                ViewHolder viewHolder;
                if (null != tag) {
                    viewHolder = (ViewHolder) tag;
                } else {
                    viewHolder = new ViewHolder(view);
                    view.setTag(viewHolder);
                }

                viewHolder.bindData(dataList.get(index));
            }

            @Override
            public Object getItem(int index) {
                return dataList.get(index);
            }

            @Override
            public Rect obtainDraggableArea(View view) {
                // 可滑动区域定制，该函数只会调用一次
                View contentView = view.findViewById(R.id.card_item_content);
                View topLayout = view.findViewById(R.id.card_top_layout);
                View bottomLayout = view.findViewById(R.id.card_bottom_layout);
                int left = view.getLeft() + contentView.getPaddingLeft() + topLayout.getPaddingLeft();
                int right = view.getRight() - contentView.getPaddingRight() - topLayout.getPaddingRight();
                int top = view.getTop() + contentView.getPaddingTop() + topLayout.getPaddingTop();
                int bottom = view.getBottom() - contentView.getPaddingBottom() - bottomLayout.getPaddingBottom();
                return new Rect(left, top, right, bottom);
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareDataList();
                imageSlidePanel.getAdapter().notifyDataSetChanged();
            }
        }, 500);

        TestPersonalCenterItemAdapter adapter = new TestPersonalCenterItemAdapter();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("eeeee" + i);
        }
        adapter.replaceData(list);

        rc_home_rm.setAdapter(adapter);
        rc_home_rm.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {

            }
        });
    }

    private void prepareDataList() {
        for (int i = 0; i < 6; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataItem.userName = names[i];
            dataItem.imagePath = imagePaths[i];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            dataList.add(dataItem);
        }
    }

    private void appendDataList() {
        for (int i = 0; i < 6; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataItem.userName = "From Append";
            dataItem.imagePath = imagePaths[8];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            dataList.add(dataItem);
        }
    }

    @OnClick(R.id.notify_change)
    public void onClick() {
        appendDataList();
        imageSlidePanel.getAdapter().notifyDataSetChanged();
    }

    @OnClick({R.id.rl_unlike, R.id.rl_details, R.id.rl_like, R.id.po_image2, R.id.po_image3})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.rl_unlike:
                break;
            case R.id.rl_details:
                break;
            case R.id.rl_like:
                break;
            case R.id.po_image2:
                break;
            case R.id.po_image3:
                break;
        }
    }

    class ViewHolder {

        ImageView imageView;
        View maskView;
        TextView userNameTv;
        TextView imageNumTv;
        TextView likeNumTv;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.card_image_view);
            maskView = view.findViewById(R.id.maskView);
            userNameTv = (TextView) view.findViewById(R.id.card_user_name);
            imageNumTv = (TextView) view.findViewById(R.id.card_pic_num);
            likeNumTv = (TextView) view.findViewById(R.id.card_like);
        }

        public void bindData(CardDataItem itemData) {
            Glide.with(context).load(itemData.imagePath).into(imageView);
            userNameTv.setText(itemData.userName);
            imageNumTv.setText(itemData.imageNum + "");
            likeNumTv.setText(itemData.likeNum + "");
        }
    }
}