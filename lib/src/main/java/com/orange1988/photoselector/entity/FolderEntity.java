package com.orange1988.photoselector.entity;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class FolderEntity {

    public String name;

    public int size;

    public String path;

    public boolean isChecked;

    public FolderEntity(String name) {
        this.name = name;
    }

    public FolderEntity(String name, int size) {
        this(name);
        this.size = size;
    }

    public FolderEntity(String name, int size, boolean isChecked) {
        this(name, size);
        this.isChecked = isChecked;
    }

}
