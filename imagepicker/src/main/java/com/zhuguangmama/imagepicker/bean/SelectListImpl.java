package com.zhuguangmama.imagepicker.bean;

import android.os.Parcelable;

import java.util.List;

public interface SelectListImpl extends Parcelable {

    String getName();

    String getSuperiorName();

    String getCode();

    String getSuperiorCode();

    boolean isSelect();

    void setSelect(boolean isSelect);

    List<? extends SelectListImpl> getChild();
}