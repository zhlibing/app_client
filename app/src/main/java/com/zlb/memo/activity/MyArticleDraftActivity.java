package com.zlb.memo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.vise.xsnow.event.IEvent;
import com.vise.xsnow.event.Subscribe;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshRecyclerAdapter;
import com.zlb.memo.bean.Note;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.bean.TestModel;
import com.zlb.memo.db.rich.NoteDao;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.utils.ImageUtil;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;
import com.zlb.memo.utils.StatusHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.layout.simple_list_item_2;

/**
 * Created by Administrator on 2017/12/5.
 */

public class MyArticleDraftActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ly_status)
    LinearLayout lyStatus;

    private List<Note> noteList = new ArrayList<>();
    private NoteDao noteDao;

    private int groupId;//分类ID
    private String groupName;
    private BaseQuickAdapter<Note, BaseViewHolder> adapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_article_draft);
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
        noteDao = new NoteDao(this);
        initView();
        SoftHideKeyBoardUtil.assistActivity(this);
    }

    @Override
    protected boolean isRegisterEvent() {
        return true;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<Note, BaseViewHolder>(R.layout.item_my_article_draft) {
            @Override
            protected void convert(final BaseViewHolder helper, final Note item) {
                SuperTextView tv_photo_number = helper.getView(R.id.tv_photo_number);
                ImageView img_cover = helper.getView(R.id.img_cover);
//                tv_photo_number.setText(item.getImagesSize());
//                ImageUtil.loadImage(context, img_cover, item.getFirstImageUrl());
                TextView tv_name = helper.getView(R.id.tv_name);
                TextView tv_detail = helper.getView(R.id.tv_detail);
                TextView tv_time = helper.getView(R.id.tv_time);
                ImageView img_delete = helper.getView(R.id.img_delete);
                tv_name.setText(item.getTitle());
                tv_detail.setText(item.getContent());
                tv_time.setText(item.getCreateTime());
                img_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(mContext);//提示弹窗
                        rxDialogSureCancel.getTitleView().setText("温馨提示");
                        rxDialogSureCancel.setContent("确定删除吗？");
                        rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                noteDao.deleteNote(item.getId());
                                noteList.remove(item);
                                showIsEmpty();
                                rxDialogSureCancel.cancel();
                            }
                        });
                        rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rxDialogSureCancel.cancel();
                            }
                        });
                        rxDialogSureCancel.show();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        noteList = noteDao.queryNotesAll(groupId);
        showIsEmpty();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyArticleDraftActivity.this, PublishBozhuDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", noteList.get(position));
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
    }

    public void showIsEmpty() {
        if (noteList != null
                && noteList.size() > 0) {
            adapter.replaceData(noteList);
        } else {
            adapter.replaceData(noteList);
            StatusHelper statusHelper = new StatusHelper(this, lyStatus, R.layout._loading_layout_empty);
            statusHelper.getmStatusLayoutManager().showEmptyView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe()
    public void refresh(IEvent event) {
        if (event != null && event instanceof PublishEvent) {
            if (((PublishEvent) event).getMsg().equals("OK")) {
                noteList = noteDao.queryNotesAll(groupId);
                showIsEmpty();
            }
        }
    }
}