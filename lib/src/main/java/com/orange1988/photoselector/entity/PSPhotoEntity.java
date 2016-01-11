package com.orange1988.photoselector.entity;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PSPhotoEntity {

    public static final String FLAG_CAMERA_PATH = "orange1988_camera_path";

    public String name;

    public String path;

    public boolean isChecked;

    public PSPhotoEntity() {

    }

    public PSPhotoEntity(String name) {
        this.name = name;
    }

    public PSPhotoEntity(String name, boolean isChecked) {
        this(name);
        this.isChecked = isChecked;
    }

    public PSPhotoEntity(String name, String path, boolean isChecked) {
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
        if (!(o instanceof PSPhotoEntity)) {
            return false;
        }
        PSPhotoEntity entity = (PSPhotoEntity) o;
        if (path != null && path.equals(entity.path)) {
            return true;
        } else {
            return false;
        }
    }

    public PSPhotoEntity copy() {
        return new PSPhotoEntity(name, path, isChecked);
    }


}
