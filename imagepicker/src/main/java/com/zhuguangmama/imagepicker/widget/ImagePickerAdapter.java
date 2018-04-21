package com.zhuguangmama.imagepicker.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhuguangmama.imagepicker.AndroidImagePicker;
import com.zhuguangmama.imagepicker.R;
import com.zhuguangmama.imagepicker.base.CommonAdapter;
import com.zhuguangmama.imagepicker.base.CommonViewHolder;
import com.zhuguangmama.imagepicker.bean.ImageItem;
import com.zhuguangmama.imagepicker.ui.ImagePagerActivity;


import java.util.ArrayList;
import java.util.List;

public class ImagePickerAdapter extends CommonAdapter<ImageItem> {
    private OnImageChangeListener listener;
    private int sizeLimit = 9;
    private String bindValue;
    private int bindId;
    private List<ImageItem> datas;
    // private int imageCount=10;
    public ImagePickerAdapter(Context context, List<ImageItem> datas,
                              int layoutId) {
        super(context, datas, layoutId);
    }

    public ImagePickerAdapter(Context context, List<ImageItem> datas,
                              int layoutId, int sizeLimit) {
        this(context, datas, layoutId);
        this.sizeLimit = sizeLimit;
        this.datas=datas;
    }

    @Override
    public void convert(final CommonViewHolder holder, final ImageItem imageItem) {
        ImageView ivPicker = holder.getView(R.id.iv_picker);
        ImageButton ivBtnDelete = holder.getView(R.id.iv_btn_delete);
        final int position = holder.getmPosition();
        if (!imageItem.isAdd) {
            ivPicker.setVisibility(View.VISIBLE);
            ivPicker.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> imgUrls=new ArrayList<String>();
                    try{
                        for (int i=0;i<datas.size()-1;i++){
                            imgUrls.add(datas.get(i).path);
                        }
                    }catch (Exception e){

                    }
                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(v.getMeasuredWidth(), v.getMeasuredHeight());
                    ImagePagerActivity.startImagePagerActivity(mContext, imgUrls, position, imageSize);
                }
            });
//            ivPicker.setTag("file://" + imageItem.path);
//            if (ivPicker.getTag() != null
//                    && ivPicker.getTag().equals("file://" + imageItem.path)) {
//                ImageLoader.getInstance().displayImage(
//                        (String) (ivPicker.getTag()), ivPicker,
//                        ImageLoaderOption.getInstance());
            Glide.with(mContext).load(imageItem.path).into(ivPicker);

//            }
            ivBtnDelete.setVisibility(View.VISIBLE);
            ivBtnDelete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mDatas.remove(imageItem);
                    int limit = AndroidImagePicker.getInstance().getSelectLimit();
                    AndroidImagePicker.getInstance().deleteSelectedImageItem(holder.getmPosition(), imageItem);
                    AndroidImagePicker.getInstance().setSelectLimit(limit + 1);
                    ImageItem addItem = new ImageItem("", "", 1);
                    addItem.isAdd = true;
                    sortListData(addItem);
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onDelete(imageItem);
                    }
                }
            });
        } else if (imageItem.isAdd) {
            ivBtnDelete.setOnClickListener(null);
            ivBtnDelete.setVisibility(View.GONE);
            ivPicker.setVisibility(View.VISIBLE);
            Glide.clear(ivPicker);
            ivPicker.setImageResource(R.drawable.pic_add_photo);
            ivPicker.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        listener.onAddBtnClick(bindId,holder.getmPosition());
                    }
                }
            });
            if (getCount() == sizeLimit + 1) {
                ivBtnDelete.setOnClickListener(null);
                ivBtnDelete.setVisibility(View.GONE);
                ivPicker.setVisibility(View.GONE);
            }
        }
    }

    public interface OnImageChangeListener {
        void onAddBtnClick(int bindId,int posiont);
        void onDelete(ImageItem item);
    }

    public void setOnImageChangeListener(OnImageChangeListener clickListener) {
        this.listener = clickListener;
    }

    /**
     * @param item
     */
    public void sortListData(ImageItem item) {
        if (mDatas.size() == 0) {
            return;
        } else {
//            mDatas.remove(item);
//            mDatas.add(item);
            boolean isHaveAdd = false;
            int index = -1;
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                if (mDatas.get(i).isAdd) {
                    isHaveAdd = true;
                    index = i;
                    mDatas.add(size, item);
                }
            }
            if(index!=-1)
                mDatas.remove(index);
            if(mDatas.size()<=sizeLimit){//图片数量小于规定数量时，如果没有添加按钮图片就加一条
                if(!isHaveAdd){
                    mDatas.add(mDatas.size(), item);
                }
            }else {//图片数量等于大于规定数量时，如果有添加按钮图片就删除
                if(isHaveAdd){
                    mDatas.remove(mDatas.size()-1);
                }
            }
            if(mDatas.size()<=0){
                mDatas.add(item);
            }
        }
    }

    public void setBindId(int bindId) {
        this.bindId = bindId;
    }

    public int getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }
}
