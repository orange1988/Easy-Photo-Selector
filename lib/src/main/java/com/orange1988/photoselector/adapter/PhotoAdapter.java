package com.orange1988.photoselector.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.base.BaseListAdapter;
import com.orange1988.photoselector.base.BaseViewHolder;
import com.orange1988.photoselector.entity.PhotoEntity;
import com.orange1988.photoselector.util.SystemUtils;
import com.orange1988.photoselector.view.PhotoItemView;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PhotoAdapter extends BaseListAdapter<PhotoEntity>{

    private Context mContext;
    private PhotoItemView.IPhotoItem iPhotoItem;
    private int itemWidth;
    private AbsListView.LayoutParams itemLayoutParams;
    private static final int NUM_COLUMNS = 3;

    public PhotoAdapter(Context context, PhotoItemView.IPhotoItem iPhotoItem) {
        super(context);
        this.mContext = context;
        this.iPhotoItem = iPhotoItem;
        this.initItemLayoutParams();
    }

    @Override
    public void render(int position, BaseViewHolder baseHolder, PhotoEntity item) {
        if (baseHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) baseHolder;
            holder.photoItemView.setData(item, position, iPhotoItem);
        }
    }

    @Override
    public BaseViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public View getConvertView() {
        PhotoItemView view = new PhotoItemView(mContext);
        view.setLayoutParams(itemLayoutParams);
        return view;
    }


    private static class ViewHolder extends BaseViewHolder {

        public PhotoItemView photoItemView;

        public ViewHolder(View view) {
            super(view);
            this.photoItemView = (PhotoItemView) view;
        }
    }

    private void initItemLayoutParams() {
        int screenWidth = SystemUtils.getWidthPixels(mContext);
        int horizontalSpacing = context.getResources().getDimensionPixelSize(R.dimen.photo_item_gv_horizontal_spacing);
        this.itemWidth = (screenWidth - (horizontalSpacing * (NUM_COLUMNS - 1))) / NUM_COLUMNS;
        this.itemLayoutParams = new AbsListView.LayoutParams(itemWidth, itemWidth);
    }
}
