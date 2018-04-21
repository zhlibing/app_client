package com.zlb.memo.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.haozhang.lib.SlantedTextView;
import com.zlb.memo.R;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshRecyclerAdapter;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshViewHolder;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.widgets.ExpandTextView;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class RewardFragmentAdapterRefresh extends BaseRefreshRecyclerAdapter<PublishBase> {

    public RewardFragmentAdapterRefresh(@LayoutRes int layoutId) {
        super(layoutId);
    }

    @Override
    protected void onBindViewHolder(BaseRefreshViewHolder holder, PublishBase model, int position) {
        TagContainerLayout ly_tags = holder.getView(R.id.ly_tags);
        ExpandTextView expandTextView = holder.getView(R.id.tv_content);
        SlantedTextView tv_jingji = holder.getView(R.id.tv_jingji);
        expandTextView.setText(model.getContentDetails());
        String[] strings = model.getTags().replace("[", "").replace("]", "").split(",");
        List<String> tags = new ArrayList<>();
        for (String tag : strings) {
            if (tag.length() > 0) {
                tags.add(tag);
            }
        }
        if (tags.size() > 0) {
            ly_tags.setTags(tags);
            ly_tags.setVisibility(View.VISIBLE);
            if (tags.contains("紧急")) {
                tv_jingji.setVisibility(View.VISIBLE);
                tv_jingji.setSlantedBackgroundColor(Color.RED);
                tv_jingji.setText("紧急");
            } else if (tags.contains("有偿")) {
                tv_jingji.setVisibility(View.VISIBLE);
                tv_jingji.setSlantedBackgroundColor(Color.GREEN);
                tv_jingji.setText("有偿");
            } else {
                tv_jingji.setVisibility(View.GONE);
            }
        } else {
            ly_tags.setVisibility(View.GONE);
            tv_jingji.setVisibility(View.GONE);
        }
//        expandTextView.setExpand(circleItem.isExpand());
//        expandTextView.setExpandStatusListener(new ExpandTextView.ExpandStatusListener() {
//            @Override
//            public void statusChange(boolean isExpand) {
//                circleItem.setExpand(isExpand);
//            }
//        });
    }
}
