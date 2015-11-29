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

    public FolderEntity(String name, int size, String path, boolean isChecked) {
        this(name, size);
        this.path = path;
        this.isChecked = isChecked;
    }

    public void increaseSize() {
        size++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof FolderEntity)) {
            return false;
        }
        FolderEntity entity = (FolderEntity) o;
        if (name != null && name.equals(entity.name)) {
            return true;
        } else {
            return false;
        }
    }

}
