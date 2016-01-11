package com.orange1988.photoselector.core;

import com.orange1988.photoselector.entity.PSPhotoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Orange on 15/11/30.
 */
public class PSManager {

    private static PSManager instance;

    private List<PSPhotoEntity> photos;

    public static PSManager getInstance() {
        if (instance == null) {
            instance = new PSManager();
        }
        return instance;
    }

    private PSManager() {
        photos = new ArrayList<>();
    }

    public List<PSPhotoEntity> getPhotos() {
        return photos;
    }

    public boolean addPhoto(PSPhotoEntity photo) {
        if (photo == null) {
            return false;
        }
        if (contain(photo)) {
            return true;
        }
        return photos.add(photo);
    }

    public boolean remove(PSPhotoEntity photo) {
        if (photo == null) {
            return false;
        }
        for (PSPhotoEntity pe : photos) {
            if (pe.equals(photo)) {
                return photos.remove(pe);
            }
        }
        return false;
    }

    public void clear() {
        photos.clear();
    }

    public boolean contain(PSPhotoEntity photo) {
        if (photo == null) {
            return false;
        }
        for (PSPhotoEntity pe : photos) {
            if (pe.equals(photo)) {
                return true;
            }

        }
        return false;
    }

    public List<PSPhotoEntity> copyPhotos() {
        List<PSPhotoEntity> list = new ArrayList<>();
        PSPhotoEntity newPhoto;
        for (PSPhotoEntity photo : photos) {
            newPhoto = photo.copy();
            list.add(newPhoto);
        }
        return list;
    }

}
