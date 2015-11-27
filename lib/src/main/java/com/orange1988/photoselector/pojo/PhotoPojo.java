package com.orange1988.photoselector.pojo;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PhotoPojo {

    public static final String FLAG_CAMERA_PATH = "orange1988_camera_path";

    public String name;

    public String path;

    public boolean isChecked;

    public PhotoPojo() {

    }

    public PhotoPojo(String name) {
        this.name = name;
    }

    public PhotoPojo(String name, boolean isChecked) {
        this(name);
        this.isChecked = isChecked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof PhotoPojo)) {
            return false;
        }
        PhotoPojo pojo = (PhotoPojo) o;
        if (path != null && path.equals(pojo.path)) {
            return true;
        } else {
            return false;
        }
    }
}
