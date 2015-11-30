package com.orange1988.photoselector.entity;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PhotoEntity {

    public static final String FLAG_CAMERA_PATH = "orange1988_camera_path";

    public String name;

    public String path;

    public boolean isChecked;

    public PhotoEntity() {

    }

    public PhotoEntity(String name) {
        this.name = name;
    }

    public PhotoEntity(String name, boolean isChecked) {
        this(name);
        this.isChecked = isChecked;
    }

    public PhotoEntity(String name, String path, boolean isChecked) {
        this(name, isChecked);
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof PhotoEntity)) {
            return false;
        }
        PhotoEntity entity = (PhotoEntity) o;
        if (path != null && path.equals(entity.path)) {
            return true;
        } else {
            return false;
        }
    }

    public PhotoEntity copy(PhotoEntity photoEntity) {
        if (photoEntity == null) {
            throw new IllegalArgumentException("the parameter can't be null");
        }
        return new PhotoEntity(photoEntity.name, photoEntity.path, photoEntity.isChecked);
    }


}
