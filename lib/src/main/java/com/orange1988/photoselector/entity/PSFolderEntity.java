package com.orange1988.photoselector.entity;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PSFolderEntity {

    public String name;

    public int size;

    public String path;

    public boolean isChecked;

    public PSFolderEntity(String name) {
        this.name = name;
    }

    public PSFolderEntity(String name, int size) {
        this(name);
        this.size = size;
    }

    public PSFolderEntity(String name, int size, String path, boolean isChecked) {
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
        if (!(o instanceof PSFolderEntity)) {
            return false;
        }
        PSFolderEntity entity = (PSFolderEntity) o;
        if (name != null && name.equals(entity.name)) {
            return true;
        } else {
            return false;
        }
    }

}
