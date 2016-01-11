package com.orange1988.photoselector.core;

import android.content.Context;
import android.text.TextUtils;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.base.PSLoader;
import com.orange1988.photoselector.base.IPSDomain;
import com.orange1988.photoselector.entity.PSPhotoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Orange on 15/11/27.
 */
public class PhotoLoader extends PSLoader<PSPhotoEntity> {

    private String folderName;
    private List<PSPhotoEntity> mPhotos;

    public PhotoLoader(Context context, IPSDomain domain) {
        super(context, domain);
    }

    public void setFolderName(String name) {
        this.folderName = name;
    }

    public List<PSPhotoEntity> getPhotos() {
        return mPhotos;
    }

    @Override
    public List<PSPhotoEntity> loadInBackground() {
        if (domain instanceof PhotoDomain) {
            PhotoDomain photoDomain = (PhotoDomain) domain;
            if (!TextUtils.isEmpty(folderName) && !folderName.equals(getContext().getResources().getString(R.string.all_photos))) {
                return photoDomain.getItems(folderName);
            } else {
                return photoDomain.getItems();
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void onCanceled(List<PSPhotoEntity> photos) {
        super.onCanceled(photos);
        onReleaseResource(photos);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mPhotos != null) {
            onReleaseResource(mPhotos);
            mPhotos = null;
        }
    }

    @Override
    protected void onStartLoading() {
        if (mPhotos != null) {
            deliverResult(mPhotos);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    public void deliverResult(List<PSPhotoEntity> photos) {
        if (isReset()) {
            if (photos != null) {
                onReleaseResource(photos);
            }
        }

        List<PSPhotoEntity> oldPhotos = photos;
        mPhotos = photos;

        if (isStarted()) {
            super.deliverResult(photos);
        }
        if (oldPhotos != null) {
            onReleaseResource(oldPhotos);
        }
    }

    protected void onReleaseResource(List<PSPhotoEntity> photos) {

    }

}
