package com.zlb.memo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.zlb.memo.R;
import com.zlb.memo.bean.PublishBozhu;
import com.zlb.memo.utils.ImageUtil;

public class HomeTravelsPhotoItemAdapter extends BaseQuickAdapter<PublishBozhu, BaseViewHolder> {
    private Context context;

    public HomeTravelsPhotoItemAdapter(Context context) {
        super(R.layout.item_home_travels_photo);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PublishBozhu item) {
        SuperTextView tv_photo_number = helper.getView(R.id.tv_photo_number);
        ImageView img_cover = helper.getView(R.id.img_cover);
        tv_photo_number.setText(item.getImagesSize());
        ImageUtil.loadImage(context, img_cover, item.getFirstImageUrl());
    }
}
