package com.orange1988.photoselector.pojo;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class FolderPojo {

    public String name;

    public int size;

    public boolean isChecked;

    public FolderPojo(String name) {
        this.name = name;
    }

    public FolderPojo(String name, int size) {
        this(name);
        this.size = size;
    }

    public FolderPojo(String name, int size, boolean isChecked) {
        this(name, size);
        this.isChecked = isChecked;
    }

}
