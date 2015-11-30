package com.orange1988.photoselector.core;

import android.content.Context;
import android.text.TextUtils;

import com.orange1988.photoselector.base.BaseLoader;
import com.orange1988.photoselector.base.IDomain;
import com.orange1988.photoselector.entity.PhotoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Orange on 15/11/27.
 */
public class PhotoLoader extends BaseLoader<PhotoEntity> {

    private String folderName;
    private List<PhotoEntity> mPhotos;

    public PhotoLoader(Context context, IDomain domain) {
        super(context, domain);
    }

    public void setFolderName(String name) {
        this.folderName = name;
    }

    public List<PhotoEntity> getPhotos() {
        return mPhotos;
    }

    @Override
    public List<PhotoEntity> loadInBackground() {
        if (domain instanceof PhotoDomain) {
            PhotoDomain photoDomain = (PhotoDomain) domain;
            if (!TextUtils.isEmpty(folderName)) {
                return photoDomain.getItems(folderName);
            } else {
                return photoDomain.getItems();
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void onCanceled(List<PhotoEntity> photos) {
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
    public void deliverResult(List<PhotoEntity> photos) {
        if (isReset()) {
            if (photos != null) {
                onReleaseResource(photos);
            }
        }

        List<PhotoEntity> oldPhotos = photos;
        mPhotos = photos;
        if (isStarted()) {
            super.deliverResult(photos);
        }
        if (oldPhotos != null) {
            onReleaseResource(oldPhotos);
        }
    }

    protected void onReleaseResource(List<PhotoEntity> photos) {

    }

}
