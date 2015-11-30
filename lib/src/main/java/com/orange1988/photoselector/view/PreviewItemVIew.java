package com.orange1988.photoselector.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.entity.PhotoEntity;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PreviewItemVIew extends LinearLayout implements PhotoViewAttacher.OnPhotoTapListener {

    private PhotoView photoView;
    private IPreviewItem iPreviewItem;
    private PhotoEntity photo;
    private int position;

    public PreviewItemVIew(Context context) {
        super(context);
        inflate(getContext(), R.layout.preview_item, this);
        photoView = (PhotoView) findViewById(R.id.photo_pv);
        photoView.setOnPhotoTapListener(this);
    }

    public void setData(PhotoEntity photo, int position, IPreviewItem iPreviewItem) {
        this.photo = photo;
        this.position = position;
        this.iPreviewItem = iPreviewItem;
        this.loadImage(photo.path);
    }

    private void loadImage(String path) {
        Picasso.with(getContext()).load("file://" + path).resize(720, 0).placeholder(R.drawable.icon_photo_default).into(photoView);
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        if (iPreviewItem != null) {
            iPreviewItem.onPhotoClickListener(photo, position);
        }
    }

    public interface IPreviewItem {

        void onPhotoClickListener(PhotoEntity photo, int position);

        int getMaxSelectedSize();
    }


    public void release() {

    }


}
