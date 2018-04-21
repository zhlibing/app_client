package com.zlb.memo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.sendtion.xrichtext.RichTextView;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.bean.Note;
import com.zlb.memo.utils.ImageUtil;
import com.zlb.memo.utils.StatusBarUtil;
import com.zlb.memo.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.vondear.rxtools.view.RxToast.showToast;

/**
 * Created by Administrator on 2017/12/12.
 */

public class PublishedBoZhuDetailsActivity extends BaseActivity {
    @BindView(R.id.parallax)
    ImageView parallax;
    @BindView(R.id.tv_attention)
    RoundTextView tvAttention;
    @BindView(R.id.tv_leaveword)
    RoundTextView tvLeaveword;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_relationship_attention)
    TextView tvRelationshipAttention;
    @BindView(R.id.tv_relationship_fans)
    TextView tvRelationshipFans;
    @BindView(R.id.ly_relationship)
    LinearLayout lyRelationship;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.panel)
    RelativeLayout panel;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.visitor)
    TextView visitor;
    @BindView(R.id.panel_lyt)
    RelativeLayout panelLyt;
    @BindView(R.id.collapse)
    CollapsingToolbarLayout collapse;
    @BindView(R.id.fmc_center_dynamic)
    LinearLayout fmcCenterDynamic;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.rf_person_details)
    SmartRefreshLayout rfPersonDetails;
    @BindView(R.id.toolbar_avatar)
    CircleImageView toolbarAvatar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.buttonBarLayout)
    ButtonBarLayout buttonBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_note_title)
    TextView tvNoteTitle;
    @BindView(R.id.tv_note_time)
    TextView tvNoteTime;
    @BindView(R.id.tv_note_group)
    TextView tvNoteGroup;
    @BindView(R.id.tv_note_content)
    RichTextView tvNoteContent;
    @BindView(R.id.content_note)
    LinearLayout contentNote;

    private int mOffset = 0;
    private int mScrollY = 0;

    //private ScrollView scroll_view;
    private Note note;//笔记对象
    private String myTitle;
    private String myContent;
    private String myGroupName;

    private Subscription subsLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published_bozhu_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(PublishedBoZhuDetailsActivity.this, R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBarLayout.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    parallax.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });
        buttonBarLayout.setAlpha(0);
        toolbar.setBackgroundColor(0);
        scrollView.smoothScrollTo(0, 0);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        note = (Note) bundle.getSerializable("note");
        String URL = bundle.getString("URL");

        myTitle = note.getTitle();
        myContent = note.getContent();
        myGroupName = note.getGroupName();

        tvNoteTitle.setText(myTitle);
        tvNoteContent.post(new Runnable() {
            @Override
            public void run() {
                //showEditData(myContent);
                tvNoteContent.clearAllLayout();
                showDataSync(myContent);
            }
        });
        tvNoteTime.setText(note.getCreateTime());
        tvNoteGroup.setText(myGroupName);
//
//        if (URL!=null&&URL.length()>0){
//            ImageUtil.loadImage(this, parallax, URL);
//        }
    }

    @Override
    public void showIsEmpty() {

    }

    /**
     * 异步方式显示数据
     *
     * @param html
     */
    private void showDataSync(final String html) {
        showLoading("加载中");
        subsLoading = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                showEditData(subscriber, html);
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                        showToast("解析错误：图片不存在或已损坏");
                    }

                    @Override
                    public void onNext(String text) {
                        if (text.contains("http://")) {
                            tvNoteContent.addImageViewAtIndex(tvNoteContent.getLastIndex(), text);
                        } else {
                            tvNoteContent.addTextViewAtIndex(tvNoteContent.getLastIndex(), text);
                        }
                    }
                });
    }

    /**
     * 显示数据
     *
     * @param html
     */
    private void showEditData(Subscriber<? super String> subscriber, String html) {
        try {
            List<String> textList = StringUtils.cutStringByImgTag(html);
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                if (text.contains("<img") && text.contains("src=")) {
                    String imagePath = StringUtils.getImgSrc(text);
                    if (imagePath != null && imagePath.length() > 0) {
                        subscriber.onNext(imagePath);
                    } else {
                        showToast("图片" + 1 + "已丢失，请重新插入！");
                    }
                } else {
                    subscriber.onNext(text);
                }
            }
            subscriber.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            subscriber.onError(e);
        }
    }

    @OnClick({R.id.tv_attention, R.id.tv_leaveword, R.id.tv_relationship_attention, R.id.tv_relationship_fans, R.id.img_avatar, R.id.visitor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_attention:
                break;
            case R.id.tv_leaveword:
                break;
            case R.id.tv_relationship_attention:
                break;
            case R.id.tv_relationship_fans:
                break;
            case R.id.img_avatar:
                break;
            case R.id.visitor:
                break;
        }
    }
}
