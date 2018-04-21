package com.zlb.memo.fragment.test;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zlb.memo.R;
import com.zlb.memo.adapter.CareUserItemAdapter;
import com.zlb.memo.adapter.TestPersonalCenterItemAdapter;
import com.zlb.memo.fragment.base.BaseFragment;
import com.zlb.memo.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class FragmentHot extends BaseFragment {

    @BindView(R.id.rc_hot_user)
    RecyclerView rcHotUser;
    @BindView(R.id.rc_hot)
    RecyclerView rcHot;
    @BindView(R.id.rf_hot)
    SmartRefreshLayout rfHot;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment_hot, container, false);
        ButterKnife.bind(this, view);
        //状态栏透明和间距处理
        StatusBarUtil.setPaddingSmart(context, view.findViewById(R.id.app_bar));
        rcHot.addItemDecoration(new DividerItemDecoration(context, VERTICAL));
        rcHot.setLayoutManager(new LinearLayoutManager(context));
//        rcPersonalCenter.setAdapter(new BaseRefreshRecyclerAdapter<Void>(buildData(), simple_list_item_2) {
//            @Override
//            protected void onBindViewHolder(SmartViewHolder holder, Void model, int position) {
//                holder.text(android.R.id.text1, String.format(Locale.CHINA, "第%02d条数据", position));
//                holder.text(android.R.id.text2, String.format(Locale.CHINA, "这是测试的第%02d条数据", position));
//                holder.textColorId(android.R.id.text2, R.color.red);
//            }
//        });
        TestPersonalCenterItemAdapter adapter = new TestPersonalCenterItemAdapter();
        rcHot.setAdapter(adapter);

        CareUserItemAdapter adapter2 = new CareUserItemAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        rcHotUser.setLayoutManager(linearLayoutManager);
        rcHotUser.setAdapter(adapter2);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("eeeee" + i);
        }
        adapter.replaceData(list);
        adapter2.replaceData(list);

        //添加Header
        View header = LayoutInflater.from(context).inflate(R.layout.test_fragment_personal_center_head, rcHot, false);
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