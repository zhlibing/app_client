package com.zhuguangmama.imagepicker.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.GridView;


import com.zhuguangmama.imagepicker.AndroidImagePicker;
import com.zhuguangmama.imagepicker.R;
import com.zhuguangmama.imagepicker.bean.ImageItem;
import com.zhuguangmama.imagepicker.bean.ImageSet;
import com.zhuguangmama.imagepicker.ui.ImagesGridActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @ClassName: RYAddPictureView
 * @Description: 软云通用添加图片的view，注意监听的注册和销毁，和onactivityresult的回调，可以设置sizeLimit控制图片数量
 * @date 2016/4/29 8:57
 */
public class RYAddPictureView extends GridView implements ImagePickerAdapter.OnImageChangeListener,
        AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImagePickCompleteListener {


    Context mContext;

    public  int requestCode = 1433;

    private List<ImageItem> imageList = new ArrayList<>();
    private int sizeLimit = 1;

    ImagePickerAdapter adapter;

    ImageItem addItem;

    ImageSet imageSetCache = new ImageSet();



    public RYAddPictureView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        initImagePicker();
    }

    private void initImagePicker() {
        /** S imagepicker */
        imageList = new ArrayList<>();
        addItem = new ImageItem("", "", 1);
        addItem.isAdd = true;
        imageList.add(addItem);
        adapter = new ImagePickerAdapter(mContext, imageList, R.layout.grid_item_select_image, sizeLimit);
        setAdapter(adapter);
        adapter.setOnImageChangeListener(this);

        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit);
        /** E imagepicker */
    }

    /**
     * @description: 调用时需要在onStart加上
     * @author: Daniel
     */
    public void setOnListeners() {
        AndroidImagePicker.getInstance().addOnPictureTakeCompleteListener(this);
        AndroidImagePicker.getInstance().addOnImagePickCompleteListener(this);
    }

    /**
     * @description: 调用时需要在onDestroy加上
     * @author: Daniel
     */
    public void destroyListeners() {
        AndroidImagePicker.getInstance().removeOnPictureTakeCompleteListener(this);
        AndroidImagePicker.getInstance().removeOnImagePickCompleteListener(this);
        if (imageList != null) {
            imageList.clear();
            imageList = null;
        }
    }

   /* @Override
    public void onAddBtnClick() {

    }*/

    @Override
    public void onAddBtnClick(int bindId, int posiont) {
        handleAddImageClick();
    }

    @Override
    public void onDelete(ImageItem item) {
        imageList = adapter.getData();
        imageSetCache.imageItems = imageList;
       // AppBus.getInstance().post(new EventInfo(C.Event.IMAGE_LIST_CHANGED,imageSetCache));
        if(onPickResultChangedListener!=null)
         onPickResultChangedListener.onPicDelete(item);
    }

    /**
     * @description: 添加图片调用接口
     * @author: Daniel
     */
    private void handleAddImageClick() {
        Intent intent = new Intent();
        AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_MULTI);
        AndroidImagePicker.getInstance().setShouldShowCamera(true);
        AndroidImagePicker.getInstance().setRequestCode(requestCode);
        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit - (adapter.getCount() - 1));
        intent.setClass(mContext, ImagesGridActivity.class);
        ((Activity) mContext).startActivityForResult(intent, requestCode);
    }

    /**
     * @description: 更新图片列表
     * @author: Daniel
     */
    private void refreshGrid() {
        adapter.setData(this.imageList);
        adapter.sortListData(addItem);
        adapter.notifyDataSetChanged();
        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit - (adapter.getCount() - 1));
        imageSetCache.imageItems = imageList;
       // AppBus.getInstance().post(new EventInfo(C.Event.IMAGE_LIST_CHANGED,imageSetCache));
    }

    public  void upData(){
//        adapter.setData(this.imageList);
//        adapter.sortListData(addItem);
        adapter.notifyDataSetChanged();
    }

    /**
      * 编辑时初始化组装自己的list item 传入
      *@author zhangsan
      *@date   16/9/8 下午2:39
      */
    public void refreshImage(List<ImageItem> imageItems){
        adapter.setData(imageItems);
        adapter.sortListData(addItem);
        adapter.notifyDataSetChanged();
        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit - (adapter.getCount() - 1));
    }


    /**
     * @description: 选完图片onActivityResult需要调用该方法
     * @author: Daniel
     */
    public void onImageActivityResult() {
        imageList.addAll(AndroidImagePicker.getInstance().getSelectedImages());
        refreshGrid();
    }

    @Override
    public void onPictureTakeComplete(String picturePath,int requestCode) {
        if(requestCode == this.requestCode) {
            imageList.add(new ImageItem(picturePath, "", 1));
            refreshGrid();
        }
    }

    public void clearImageList(List<ImageItem> items){
        if(imageList==null){
            imageList = new ArrayList<>();
        }
        imageList.clear();
        ImageItem imageItemAdd = new ImageItem(AndroidImagePicker.getInstance().getCurrentPhotoPath(), "", 1);
        imageItemAdd.isAdd = true;
        imageList.add(imageItemAdd);
        imageList.addAll(items);
    }

    @Override
    public void onImagePickComplete(List<ImageItem> items,int requestCode) {
//        LogX.d("panmengze","onImagePickComplete listsize = " + items.size());
        if(requestCode == this.requestCode) {
            imageList.addAll(items);
            refreshGrid();
        }
    }

    public ImageItem getAddItem() {
        return addItem;
    }

    public void setAddItem(ImageItem addItem) {
        this.addItem = addItem;
    }

    public int getSizeLimit() {
        return sizeLimit;
    }

    /**
     * @description: 设置图片限制
     * @author: Daniel
     */
    public void setSizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit - (adapter.getCount() - 1));
        adapter.setSizeLimit(sizeLimit);
    }



    /**
     * @description: 获取输入图片列表
     * @author: Daniel
     */
    public List<ImageItem> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageItem> imageList) {
        if(imageList == null){
            imageList = new ArrayList<>();
        }
        if(imageList.size() < 1){
            addItem.isAdd = true;
            imageList.add(addItem);
        }
        this.imageList = imageList;
        adapter.setData(this.imageList);
        adapter.notifyDataSetChanged();
        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit - (adapter.getCount() - 1));
    }


    public void setOnPickResultChangedListener(RYAddPictureView.onPickResultChangedListener onPickResultChangedListener) {
        this.onPickResultChangedListener = onPickResultChangedListener;
    }
    onPickResultChangedListener onPickResultChangedListener;

   public interface  onPickResultChangedListener{
       //点击图片删除时触发
       void onPicDelete(ImageItem item);
      // void onImage
   }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
//    @Override
//    public Object clone() {
//        RYAddPictureView rYAddPictureView = null;
//        try {
//            rYAddPictureView = (RYAddPictureView) super.clone();   //浅复制
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        return rYAddPictureView;
//    }

//    @Override
//    public RYAddPictureView clone() throws CloneNotSupportedException {
//        RYAddPictureView rYAddPictureView = null;
//        rYAddPictureView = (RYAddPictureView) super.clone();
//        rYAddPictureView.imageList = new ArrayList<>();   //深度复制
//        for (ImageItem imageItem : imageList) {
////            rYAddPictureView.imageList.add(imageItem.clone());
//        }
//        return rYAddPictureView;
//    }
}
