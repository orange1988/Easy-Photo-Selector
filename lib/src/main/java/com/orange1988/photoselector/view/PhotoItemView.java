package com.orange1988.photoselector.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.core.PSManager;
import com.orange1988.photoselector.entity.PhotoEntity;
import com.squareup.picasso.Picasso;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PhotoItemView extends LinearLayout implements View.OnClickListener {

    private ImageButton checkView;
    private ImageView imageView;
    private View maskView;

    private IPhotoItem iPhotoItem;
    private PhotoEntity photoEntity;
    private int position;

    public static final String FLAG_CAMERA = "FLAG_CAMERA_ORANGE_1988_@_2015_12_01";

    public PhotoItemView(Context context) {
        super(context);
        inflate(context, R.layout.photo_item, this);
        imageView = (ImageView) findViewById(R.id.photo_iv);
        maskView = findViewById(R.id.photo_mask);
        checkView = (ImageButton) findViewById(R.id.photo_cb);
        imageView.setOnClickListener(this);
        checkView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (iPhotoItem == null || photoEntity == null) {
            return;
        }
        if (v == imageView) {
            if (photoEntity.path != null && photoEntity.path.equals(FLAG_CAMERA)) {
                iPhotoItem.onCameraItemClick();
            } else {
                iPhotoItem.onItemClickListener(photoEntity, position);
            }
        } else if (v == checkView) {
            if (PSManager.getInstance().getPhotos().size() >= iPhotoItem.getSelectedLimit() && !photoEntity.isChecked) {
                iPhotoItem.beyondSelectedLimit();
                return;
            } else {
                setSelected(!photoEntity.isChecked);
                iPhotoItem.onCheckedChanged(photoEntity, this);
            }

        }
    }

    @Override
    public void setSelected(boolean selected) {
        if (photoEntity == null) {
            return;
        }
        maskView.setVisibility(selected ? VISIBLE : INVISIBLE);
        photoEntity.isChecked = selected;
        setBtnChecked(checkView, selected);
    }

    public void setData(PhotoEntity photoEntity, int position, final IPhotoItem iPhotoItem) {
        this.photoEntity = photoEntity;
        this.position = position;
        this.iPhotoItem = iPhotoItem;
        this.loadImage(photoEntity.path);
        this.setSelected(photoEntity.isChecked);
    }

    private void loadImage(String path) {
        if (path != null && path.equals(FLAG_CAMERA)) {
            imageView.setImageResource(R.drawable.icon_photo_default);
            checkView.setVisibility(GONE);
        } else {
            Picasso.with(getContext()).load("file://" + path).placeholder(R.drawable.icon_photo_default).resize(160, 160).centerCrop().into(imageView);
            checkView.setVisibility(VISIBLE);
        }
    }

    private void setBtnChecked(ImageButton v, boolean isChecked) {
        if (v == null) {
            return;
        }
        int res = isChecked ? R.drawable.icon_checked : R.drawable.icon_unchecked;
        v.setImageResource(res);
    }

    public interface IPhotoItem {

        int getSelectedLimit();

        void beyondSelectedLimit();

        void onCheckedChanged(PhotoEntity photoEntity, PhotoItemView view);

        void onItemClickListener(PhotoEntity photoEntity, int position);

        void onCameraItemClick();

    }
}
