package com.orange1988.photoselector.adapter;

import android.content.Context;
import android.view.View;

import com.orange1988.photoselector.base.BaseListAdapter;
import com.orange1988.photoselector.base.BaseViewHolder;
import com.orange1988.photoselector.pojo.PhotoPojo;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PhotoAdapter extends BaseListAdapter<PhotoPojo>{

    public PhotoAdapter(Context context) {
        super(context);
    }

    @Override
    public void render(int position, BaseViewHolder baseHolder, PhotoPojo item) {

    }

    @Override
    public BaseViewHolder getViewHolder(View convertView) {
        return null;
    }

    @Override
    public int getLayoutResource() {
        return 0;
    }
}
