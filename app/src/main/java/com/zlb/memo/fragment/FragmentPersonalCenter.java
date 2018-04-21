package com.zlb.memo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.vondear.rxtools.RxActivityTool;
import com.zlb.memo.R;
import com.zlb.memo.activity.MyArticleDraftActivity;
import com.zlb.memo.activity.MyPingYouDraftActivity;
import com.zlb.memo.adapter.PersonalCenterItemAdapterRefresh;
import com.zlb.memo.bean.TestModel;
import com.zlb.memo.db.rich.NoteDao;
import com.zlb.memo.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentPersonalCenter extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.rc_personal_center)
    RecyclerView rcPersonalCenter;
    @BindView(R.id.rf_personal_center)
    SmartRefreshLayout rfPersonalCenter;

    private List<TestModel> modelList = new ArrayList<>();
    private NoteDao noteDao;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, view);
        noteDao = new NoteDao(context);
        rcPersonalCenter.setLayoutManager(new LinearLayoutManager(context));
        modelList.add(new TestModel("1000"));
        modelList.add(new TestModel("0", "拼游草稿"));
        modelList.add(new TestModel("0", "文章草稿"));
        modelList.add(new TestModel("0"));
        modelList.add(new TestModel("0"));
        modelList.add(new TestModel("1"));
        modelList.add(new TestModel("0"));
        modelList.add(new TestModel("0"));
        modelList.add(new TestModel("0"));
        modelList.add(new TestModel("1"));
        modelList.add(new TestModel("0"));
        modelList.add(new TestModel("0"));
        PersonalCenterItemAdapterRefresh adapter = new PersonalCenterItemAdapterRefresh(modelList, context, this);
        rcPersonalCenter.setAdapter(adapter);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showIsEmpty() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (modelList.get(position).getB()) {
            case "文章草稿":
                RxActivityTool.skipActivity(getActivity(), MyArticleDraftActivity.class);
                break;
            case "拼游草稿":
                RxActivityTool.skipActivity(getActivity(), MyPingYouDraftActivity.class);
                break;
            default:
                break;
        }
    }
}