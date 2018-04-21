package com.zlb.memo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sendtion.xrichtext.RichTextView;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.bean.Group;
import com.zlb.memo.bean.Note;
import com.zlb.memo.db.rich.GroupDao;
import com.zlb.memo.db.rich.NoteDao;
import com.zlb.memo.utils.CommonUtil;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;
import com.zlb.memo.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.vondear.rxtools.view.RxToast.showToast;


/**
 * 笔记详情
 */
public class PublishBozhuDetailsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_note_title)
    TextView tv_note_title;
    @BindView(R.id.tv_note_time)
    TextView tv_note_time;
    @BindView(R.id.tv_note_group)
    TextView tv_note_group;
    @BindView(R.id.tv_note_content)
    RichTextView tv_note_content;
    @BindView(R.id.content_note)
    LinearLayout contentNote;
    //private ScrollView scroll_view;
    private Note note;//笔记对象
    private String myTitle;
    private String myContent;
    private String myGroupName;
    private NoteDao noteDao;
    private GroupDao groupDao;

    private ProgressDialog loadingDialog;
    private Subscription subsLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_bozhu_details);
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
        SoftHideKeyBoardUtil.assistActivity(this);
        initView();
    }

    private void initView() {
        noteDao = new NoteDao(this);
        groupDao = new GroupDao(this);

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("数据加载中...");
        loadingDialog.setCanceledOnTouchOutside(false);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        note = (Note) bundle.getSerializable("note");

        myTitle = note.getTitle();
        myContent = note.getContent();
//        Group group = groupDao.queryGroupById(note.getGroupId());
//        myGroupName = group.getName();
        myGroupName = note.getGroupName();

        tv_note_title.setText(myTitle);
        tv_note_content.post(new Runnable() {
            @Override
            public void run() {
                //showEditData(myContent);
                tv_note_content.clearAllLayout();
                showDataSync(myContent);
            }
        });
        tv_note_time.setText(note.getCreateTime());
        tv_note_group.setText(myGroupName);
    }

    /**
     * 异步方式显示数据
     *
     * @param html
     */
    private void showDataSync(final String html) {
        loadingDialog.show();

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
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.dismiss();
                        e.printStackTrace();
                        showToast("解析错误：图片不存在或已损坏");
                    }

                    @Override
                    public void onNext(String text) {
                        if (text.contains("http://")) {
                            tv_note_content.addImageViewAtIndex(tv_note_content.getLastIndex(), text);
                        } else {
                            tv_note_content.addTextViewAtIndex(tv_note_content.getLastIndex(), text);
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
//                    if (new File(imagePath).exists()) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_note_edit://编辑笔记
                Intent intent = new Intent(PublishBozhuDetailsActivity.this, PublishBoZhuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", note);
                intent.putExtra("data", bundle);
                intent.putExtra("flag", 1);//编辑笔记
                startActivity(intent);
                finish();
                break;
            case R.id.action_note_share://分享笔记
                CommonUtil.shareTextAndImage(this, note.getTitle(), note.getContent(), null);//分享图文
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showIsEmpty() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
