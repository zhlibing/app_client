package com.zlb.memo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zlb.memo.R;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshRecyclerAdapter;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshViewHolder;
import com.zlb.memo.bean.TestModel;

import java.util.Collection;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalCenterItemAdapterRefresh extends BaseRefreshRecyclerAdapter<TestModel> {
    private Context context;
    private AdapterView.OnItemClickListener mListener;

    public PersonalCenterItemAdapterRefresh(Collection<TestModel> models, Context context, AdapterView.OnItemClickListener mListener) {
        super(models, R.layout.fragment_personal_center_item);
        this.context = context;
        this.mListener = mListener;
    }

    @Override
    public BaseRefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1000:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_personal_center_head, parent, false);
                return new BaseRefreshViewHolder(view, mListener);
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_personal_center_item, parent, false);
                return new BaseRefreshViewHolder(view, mListener);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_personal_center_item_middle_line, parent, false);
                return new BaseRefreshViewHolder(view, mListener);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }

    }

    @Override
    protected void onBindViewHolder(BaseRefreshViewHolder holder, TestModel model, int position) {
        if (holder.getItemViewType() == 1000) {
            CircleImageView img_avatar = holder.getView(R.id.img_avatar);
            img_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else if (holder.getItemViewType() == 0) {
            TextView tv_menu_name = holder.getView(R.id.tv_menu_name);
            if (model.getB() != null && model.getB().length() > 0) {
                tv_menu_name.setText(model.getB());
            }
        } else if (holder.getItemViewType() == 1) {

        }

    }

    @Override
    public int getItemViewType(int position) {
        int type = 1000;
        if (getmList().get(position).getA().equals("0")) {
            type = 0;
        }
        if (getmList().get(position).getA().equals("1")) {
            type = 1;
        }
        return type;
    }
}
