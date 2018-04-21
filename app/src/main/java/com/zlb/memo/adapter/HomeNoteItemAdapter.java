package com.zlb.memo.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zlb.memo.R;
import com.zlb.memo.bean.Note;

public class HomeNoteItemAdapter extends BaseQuickAdapter<Note, BaseViewHolder> {

    public HomeNoteItemAdapter() {
        super(R.layout.list_item_note);
    }

    @Override
    protected void convert(BaseViewHolder helper, Note note) {
        //将数据保存在itemView的Tag中，以便点击时进行获取
        helper.itemView.setTag(note);
        //Log.e("adapter", "###record="+record);
        TextView tv_list_title = helper.getView(R.id.tv_list_title);
        tv_list_title.setText(note.getTitle());
        TextView tv_list_summary = helper.getView(R.id.tv_list_summary);
        tv_list_summary.setText(note.getContent());
        TextView tv_list_time = helper.getView(R.id.tv_list_time);
        tv_list_time.setText(note.getCreateTime());
        TextView tv_list_group = helper.getView(R.id.tv_list_group);
        tv_list_group.setText(note.getGroupName());
    }
}
