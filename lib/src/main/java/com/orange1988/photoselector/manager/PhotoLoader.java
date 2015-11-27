package com.orange1988.photoselector.manager;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.orange1988.photoselector.pojo.PhotoPojo;

import java.util.List;

/**
 * Created by Mr. Orange on 15/11/27.
 */
public class PhotoLoader extends AsyncTaskLoader<List<PhotoPojo>> {

    private PhotoPojoDomain domain;
    private String folderName;

    public PhotoLoader(Context context, PhotoPojoDomain domain) {
        super(context);
        this.domain = domain;
    }

    public void setFolderName(String name) {
        this.folderName = name;
    }

    @Override
    public List<PhotoPojo> loadInBackground() {
        if (!TextUtils.isEmpty(folderName)) {
            return domain.getPhotos(folderName);
        } else {
            return domain.getLatestPhotos();
        }
    }

}
