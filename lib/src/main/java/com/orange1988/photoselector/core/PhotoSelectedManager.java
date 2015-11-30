package com.orange1988.photoselector.core;

import com.orange1988.photoselector.entity.PhotoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Orange on 15/11/30.
 */
public class PhotoSelectedManager {

    private static PhotoSelectedManager instance;

    private List<PhotoEntity> photos;

    public static PhotoSelectedManager getInstance() {
        if (instance == null) {
            instance = new PhotoSelectedManager();
        }
        return instance;
    }

    private PhotoSelectedManager() {
        photos = new ArrayList<>();
    }

    public List<PhotoEntity> getPhotos() {
        return photos;
    }

    public boolean addPhoto(PhotoEntity photo) {
        if (photo == null) {
            return false;
        }
        if (contain(photo)) {
            return true;
        }
        return photos.add(photo);
    }

    public boolean remove(PhotoEntity photo) {
        if (photo == null) {
            return false;
        }
        for (PhotoEntity pe : photos) {
            if (pe.equals(photo)) {
                return photos.remove(pe);
            }
        }
        return false;
    }

    public void clear() {
        photos.clear();
    }

    public boolean contain(PhotoEntity photo) {
        if (photo == null) {
            return false;
        }
        for (PhotoEntity pe : photos) {
            if (pe.equals(photo)) {
                return true;
            }

        }
        return false;
    }

}
