package com.zlb.memo.fragment.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zlb.memo.R;
import com.zlb.memo.activity.NoteActivity;
import com.zlb.memo.adapter.HomeNoteItemAdapter;
import com.zlb.memo.bean.Note;
import com.zlb.memo.db.rich.NoteDao;
import com.zlb.memo.fragment.base.BaseFragment;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class FragmentPersonalCenter extends BaseFragment {

    @BindView(R.id.rc_personal_center)
    RecyclerView rcPersonalCenter;
    @BindView(R.id.rf_personal_center)
    SmartRefreshLayout rfPersonalCenter;

    private List<Note> noteList;
    private NoteDao noteDao;

    private int groupId;//分类ID
    private String groupName;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment_personal_center, container, false);
        ButterKnife.bind(this, view);
        noteDao = new NoteDao(context);
        rcPersonalCenter.addItemDecoration(new DividerItemDecoration(context, VERTICAL));
        rcPersonalCenter.setLayoutManager(new LinearLayoutManager(context));
//        rcPersonalCenter.setAdapter(new BaseRefreshRecyclerAdapter<Void>(buildData(), simple_list_item_2) {
//            @Override
//            protected void onBindViewHolder(SmartViewHolder holder, Void model, int position) {
//                holder.text(android.R.id.text1, String.format(Locale.CHINA, "第%02d条数据", position));
//                holder.text(android.R.id.text2, String.format(Locale.CHINA, "这是测试的第%02d条数据", position));
//                holder.textColorId(android.R.id.text2, R.color.red);
//            }
//        });
        HomeNoteItemAdapter adapter = new HomeNoteItemAdapter();
        rcPersonalCenter.setAdapter(adapter);
        noteList = noteDao.queryNotesAll(groupId);
        adapter.replaceData(noteList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, NoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", noteList.get(position));
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
        //添加Header
        View header = LayoutInflater.from(context).inflate(R.layout.test_fragment_personal_center_head, rcPersonalCenter, false);
        adapter.addHeaderView(header);
        adapter.openLoadAnimation();
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showIsEmpty() {

    }

    protected Collection<Void> buildData() {
        return Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }
}