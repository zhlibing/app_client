package com.zlb.memo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshRecyclerAdapter;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshViewHolder;
import com.zlb.memo.bean.MyPoiItem;
import com.zlb.memo.listener.ItemTouchHelperAdapter;
import com.zlb.memo.listener.SimpleItemTouchHelperCallback;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PublishPingYouEditActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.tv_confirm_publish)
    TextView tvConfirmPublish;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rc_select)
    RecyclerView rcSelect;

    private ArrayList<MyPoiItem> selectpoiItems = new ArrayList<>();// poi数据

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_publish_pingyou_edit);
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

        selectpoiItems = getIntent().getExtras().getParcelableArrayList("SELECTPOINTS");
        initView();
        SoftHideKeyBoardUtil.assistActivity(this);
    }

    private void initView() {
        LinearLayoutManager li2 = new LinearLayoutManager(this);
        rcSelect.setLayoutManager(li2);
        rcSelect.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        final BaseRefreshRecyclerAdapter ad = new BaseRefreshRecyclerAdapter<MyPoiItem>(selectpoiItems, R.layout.item_publish_pingyou_select_edit, this) {
            @Override
            protected void onBindViewHolder(BaseRefreshViewHolder holder, MyPoiItem model, final int position) {
                SuperTextView tv_num = holder.getView(R.id.tv_num);
                tv_num.setText("" + position);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_detail = holder.getView(R.id.tv_detail);
                tv_name.setText(model.getPoiItem().getTitle());
                holder.getView(R.id.tv_dellete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(mContext);//提示弹窗
                        rxDialogSureCancel.getTitleView().setText("温馨提示");
                        rxDialogSureCancel.setContent("确定删除吗？");
                        rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectpoiItems.remove(position);
                                refresh(selectpoiItems);
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
        rcSelect.setAdapter(ad);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(new ItemTouchHelperAdapter() {
            @Override
            public void onItemMove(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
                int fromPosition = source.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition) {
                    //分别把中间所有的item的位置重新交换
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(selectpoiItems, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(selectpoiItems, i, i - 1);
                    }
                }
                ad.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemDissmiss(RecyclerView.ViewHolder source) {

            }

            @Override
            public void onItemSelect(RecyclerView.ViewHolder source) {

            }

            @Override
            public void onItemClear(RecyclerView.ViewHolder source) {

            }
        }));
        itemTouchHelper.attachToRecyclerView(rcSelect);

        tvConfirmPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("SELECTPOINTS", selectpoiItems);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showIsEmpty() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RxActivityTool.skipActivity(PublishPingYouEditActivity.this, PublishPingYouEditIntruduceActivity.class);
    }
}