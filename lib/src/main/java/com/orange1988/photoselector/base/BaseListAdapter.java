package com.orange1988.photoselector.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Mr. Orange on 15/11/25.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    public final String TAG = getClass().getSimpleName();

    protected Context context;
    protected List<T> items;

    public BaseListAdapter(Context context) {
        this.context = context;
    }

    public List<T> getItems() {
        return items;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (items != null) {
            count = items.size();
        }
        return count;
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final BaseViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(context, getLayoutResource(), null);
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }

        T item = items.get(position);
        render(position, holder, item);
        return convertView;
    }

    public abstract void render(int position, BaseViewHolder baseHolder, T item);

    public abstract BaseViewHolder getViewHolder(View convertView);

    public abstract int getLayoutResource();
}
