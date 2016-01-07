package com.orange1988.photoselector.core;

import com.orange1988.photoselector.entity.PhotoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Orange on 15/11/30.
 */
public class PSManager {

    private static PSManager instance;

    private List<PhotoEntity> photos;

    public static PSManager getInstance() {
        if (instance == null) {
            instance = new PSManager();
        }
        return instance;
    }

    private PSManager() {
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

    public List<PhotoEntity> copyPhotos() {
        List<PhotoEntity> list = new ArrayList<>();
        PhotoEntity newPhoto;
        for (PhotoEntity photo : photos) {
            newPhoto = photo.copy();
            list.add(newPhoto);
        }
        return list;
    }

}
