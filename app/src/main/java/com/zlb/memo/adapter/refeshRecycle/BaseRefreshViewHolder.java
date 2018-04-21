package com.zlb.memo.adapter.refeshRecycle;

import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/12/6.
 */

public class BaseRefreshViewHolder extends BaseViewHolder implements View.OnClickListener {
    private AdapterView.OnItemClickListener mListener;

    public BaseRefreshViewHolder(View view) {
        super(view);
    }

    public BaseRefreshViewHolder(View view, AdapterView.OnItemClickListener mListener) {
        super(view);
        this.mListener = mListener;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            int position = getAdapterPosition();
            if (position >= 0) {
                mListener.onItemClick(null, v, position, getItemId());
            }
        }
    }
}
