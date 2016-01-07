package com.orange1988.photoselector.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.orange1988.photoselector.entity.PhotoEntity;
import com.orange1988.photoselector.view.PreviewItemVIew;

import java.util.List;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PSPreviewPagerAdapter extends PagerAdapter {

    private List<PhotoEntity> photos;
    private Context mContext;
    private SparseArray<PreviewItemVIew> mViews;
    private PreviewItemVIew.IPreviewItem iPreviewItem;

    public PSPreviewPagerAdapter(Context context, PreviewItemVIew.IPreviewItem iPreviewItem) {
        this.mContext = context;
        this.iPreviewItem = iPreviewItem;
        this.mViews = new SparseArray<>();
    }

    public void setItems(List<PhotoEntity> items) {
        this.photos = items;
    }

    @Override
    public int getCount() {
        return photos == null ? 0 : photos.size();
    }

    public PhotoEntity getItem(int position) {
        return photos.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PreviewItemVIew view = mViews.get(position);
        if (view == null) {
            view = new PreviewItemVIew(mContext);
            view.setData(photos.get(position), position, iPreviewItem);
            mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
