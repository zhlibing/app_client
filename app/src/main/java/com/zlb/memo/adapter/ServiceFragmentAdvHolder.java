
package com.zlb.memo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bigkoo.convenientbanner.holder.Holder;
import com.zlb.memo.bean.TestModel;
import com.zlb.memo.utils.ImageUtil;

public class ServiceFragmentAdvHolder implements Holder<TestModel> {
    private ImageView imageView;
    private Context mContext;

    public ServiceFragmentAdvHolder() {
    }

    public View createView(Context context) {
        this.imageView = new ImageView(context);
        this.imageView.setScaleType(ScaleType.CENTER_CROP);
        this.mContext = context;
        return this.imageView;
    }

    public void UpdateUI(Context context, int position, TestModel data) {
        ImageUtil.loadImage(this.mContext, this.imageView, data.getA());
    }
}
