package com.orange1988.photoselector.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.pojo.PhotoPojo;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PhotoItemView extends LinearLayout implements View.OnClickListener {

    private ImageButton checkView;
    private ImageView imageView;
    private View maskView;
    private PhotoPojo photoPojo;

    private IPhotoItemView iPhotoItemView;

    private int position;

    public PhotoItemView(Context context) {
        super(context);
        imageView = (ImageView) findViewById(R.id.photo_iv);
        maskView = findViewById(R.id.photo_mask);
        checkView = (ImageButton) findViewById(R.id.photo_cb);
    }

    @Override
    public void onClick(View v) {
        if (iPhotoItemView == null || photoPojo == null) {
            return;
        }
        if (v == imageView) {
            iPhotoItemView.onItemClickListener(photoPojo, position);
        } else if (v == checkView) {
            boolean isChecked = photoPojo.isChecked;
        }
    }

    @Override
    public void setSelected(boolean selected) {
        if (photoPojo == null) {
            return;
        }
        maskView.setVisibility(selected ? VISIBLE : INVISIBLE);
        photoPojo.isChecked = selected;
        setChecked(checkView, selected);
    }

    public void setIPhotoItemView(PhotoPojo photoPojo, int position, final IPhotoItemView iPhotoItemView) {
        this.iPhotoItemView = iPhotoItemView;
        this.photoPojo = photoPojo;
        this.position = position;
    }

    public interface IPhotoItemView {

        int getSelectedLimit();

        void beyondSelectedLimit();

        void onCheckedChanged(PhotoPojo photoPojo, PhotoItemView view, boolean isChecked);

        void onItemClickListener(PhotoPojo photoPojo, int position);

    }

    private void setChecked(ImageButton v, boolean isChecked) {
        if (v == null) {
            return;
        }
        int res = isChecked ? R.drawable.icon_checked : R.drawable.icon_unchecked;
        v.setImageResource(res);
    }

}
