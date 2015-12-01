package com.orange1988.photoselector.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.orange1988.photoselector.R;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class CameraItemView extends LinearLayout{

    private PhotoItemView.IPhotoItem iPhotoItem;

    public CameraItemView(Context context) {
        super(context);
        inflate(context, R.layout.camera_item, this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPhotoItem != null) {
                    iPhotoItem.onCameraItemClick();
                }
            }
        });
    }

    public void setData(PhotoItemView.IPhotoItem iPhotoItem) {
        this.iPhotoItem = iPhotoItem;
    }
}
