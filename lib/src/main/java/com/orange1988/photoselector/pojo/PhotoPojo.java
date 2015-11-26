package com.orange1988.photoselector.pojo;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PhotoPojo {

    public String name;

    public String path;

    public boolean isChecked;

    public PhotoPojo(String name) {
        this.name = name;
    }

    public PhotoPojo(String name, boolean isChecked) {
        this(name);
        this.isChecked = isChecked;
    }

}
