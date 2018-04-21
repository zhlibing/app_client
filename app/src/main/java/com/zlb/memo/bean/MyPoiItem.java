package com.zlb.memo.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.amap.api.services.core.PoiItem;

/**
 * Created by Administrator on 2017/12/11.
 */

public class MyPoiItem implements Parcelable {
    private boolean isSelected = false;
    private PoiItem poiItem;

    public MyPoiItem(boolean isSelected, PoiItem poiItem) {
        this.isSelected = isSelected;
        this.poiItem = poiItem;
    }

    protected MyPoiItem(Parcel in) {
        isSelected = in.readByte() != 0;
        poiItem = in.readParcelable(PoiItem.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeParcelable(poiItem, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyPoiItem> CREATOR = new Creator<MyPoiItem>() {
        @Override
        public MyPoiItem createFromParcel(Parcel in) {
            return new MyPoiItem(in);
        }

        @Override
        public MyPoiItem[] newArray(int size) {
            return new MyPoiItem[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public PoiItem getPoiItem() {
        return poiItem;
    }

    public void setPoiItem(PoiItem poiItem) {
        this.poiItem = poiItem;
    }
}
