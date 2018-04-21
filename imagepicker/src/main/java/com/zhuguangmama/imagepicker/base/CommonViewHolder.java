package com.zhuguangmama.imagepicker.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/***
 * 根据布局加载不同view的通用viewholder
 * 
 * @author jery 2015年9月23日2015年9月23日13:36:10
 * 
 */
public class CommonViewHolder {
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;

	public CommonViewHolder(Context context, ViewGroup parent, int layoutId,
							int position) {
		this.mViews = new SparseArray<View>();
		this.mPosition = position;
		this.mConvertView = LayoutInflater.from(context).inflate(layoutId,
				parent, false);
		this.mConvertView.setTag(this);
	}

	public View getmConvertView() {
		return mConvertView;
	}

	public int getmPosition() {
		return mPosition;
	}

	public void setmPosition(int mPosition) {
		this.mPosition = mPosition;
	}

	public static CommonViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (null == convertView) {
			return new CommonViewHolder(context, parent, layoutId, position);
		} else {
			CommonViewHolder holder = (CommonViewHolder) convertView.getTag();
			holder.mPosition = position;

			return holder;
		}
	}

	/***
	 * 根据view的id 获取view
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);

		if (null == view) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}

		return (T) view;
	}

	/**
	 * 根据textview id 设置text值
	 * 
	 * @param viewId
	 * @param content
	 * @return
	 */
	public CommonViewHolder setText(int viewId, String content) {
		TextView textView = getView(viewId);
		textView.setText(content);
		return this;
	}
}
