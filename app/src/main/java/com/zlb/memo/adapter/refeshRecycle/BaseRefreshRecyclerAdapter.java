package com.zlb.memo.adapter.refeshRecycle;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseRefreshRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRefreshViewHolder> implements ListAdapter {

    private final int mLayoutId;
    private final List<T> mList;
    private AdapterView.OnItemClickListener mListener;

    public BaseRefreshRecyclerAdapter(@LayoutRes int layoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>();
        this.mLayoutId = layoutId;
    }

    public BaseRefreshRecyclerAdapter(Collection<T> collection, @LayoutRes int layoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>(collection);
        this.mLayoutId = layoutId;
    }

    public BaseRefreshRecyclerAdapter(Collection<T> collection, @LayoutRes int layoutId, AdapterView.OnItemClickListener listener) {
        setHasStableIds(false);
        setOnItemClickListener(listener);
        this.mList = new ArrayList<>(collection);
        this.mLayoutId = layoutId;
    }

    @Override
    public BaseRefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRefreshViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(BaseRefreshViewHolder holder, int position) {
        onBindViewHolder(holder, position < mList.size() ? mList.get(position) : null, position);
    }

    protected abstract void onBindViewHolder(BaseRefreshViewHolder holder, T model, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public BaseRefreshRecyclerAdapter<T> setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
        return this;
    }

    public BaseRefreshRecyclerAdapter<T> refresh(Collection<T> collection) {
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }

    public BaseRefreshRecyclerAdapter<T> loadmore(Collection<T> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    public void notifyListDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    /**
     * Notifies the attached observers that the underlying data is no longer valid
     * or available. Once invoked this adapter is no longer valid and should
     * not report further data set changes.
     */
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseRefreshViewHolder holder;
        if (convertView != null) {
            holder = (BaseRefreshViewHolder) convertView.getTag();
        } else {
            holder = onCreateViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public List<T> getmList() {
        return mList;
    }
}
