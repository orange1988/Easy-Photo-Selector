package com.orange1988.photoselector.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.base.PSBaseListAdapter;
import com.orange1988.photoselector.base.PSViewHolder;
import com.orange1988.photoselector.entity.PhotoEntity;
import com.orange1988.photoselector.util.SystemUtils;
import com.orange1988.photoselector.view.PhotoItemView;

import java.util.List;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PSPhotoAdapter extends PSBaseListAdapter<PhotoEntity> {

    private PhotoItemView.IPhotoItem iPhotoItem;
    private int itemWidth;
    private AbsListView.LayoutParams itemLayoutParams;
    private boolean isNeedCameraItem;

    private static final int NUM_COLUMNS = 3;

    public PSPhotoAdapter(Context context, PhotoItemView.IPhotoItem iPhotoItem) {
        super(context);
        this.iPhotoItem = iPhotoItem;
        this.initItemLayoutParams();
    }

    public void setItems(List<PhotoEntity> items, boolean isNeedCameraItem) {
        this.isNeedCameraItem = isNeedCameraItem;
        if (isNeedCameraItem) {
            PhotoEntity photoEntity = new PhotoEntity();
            photoEntity.path = PhotoItemView.FLAG_CAMERA;
            items.add(0, photoEntity);
        }
        this.setItems(items);
    }

    @Override
    public void setItems(List<PhotoEntity> items) {
        super.setItems(items);
    }

    @Override
    public void render(int position, PSViewHolder baseHolder, PhotoEntity item) {
        if (baseHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) baseHolder;
            holder.photoItemView.setData(item, position, iPhotoItem);
        }
    }

    @Override
    public PSViewHolder getViewHolder(int position, View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public View getConvertView(int position) {
        PhotoItemView view = new PhotoItemView(mContext);
        view.setLayoutParams(itemLayoutParams);
        return view;
    }

    private static class ViewHolder extends PSViewHolder {

        public PhotoItemView photoItemView;

        public ViewHolder(View view) {
            super(view);
            this.photoItemView = (PhotoItemView) view;
        }
    }


    private void initItemLayoutParams() {
        int screenWidth = SystemUtils.getWidthPixels(mContext);
        int horizontalSpacing = this.mContext.getResources().getDimensionPixelSize(R.dimen.photo_item_gv_horizontal_spacing);
        this.itemWidth = (screenWidth - (horizontalSpacing * (NUM_COLUMNS - 1))) / NUM_COLUMNS;
        this.itemLayoutParams = new AbsListView.LayoutParams(itemWidth, itemWidth);
    }
}
