package com.orange1988.photoselector.core;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.orange1988.photoselector.base.IDomain;
import com.orange1988.photoselector.entity.PhotoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PhotoDomain implements IDomain<PhotoEntity> {

    private ContentResolver contentResolver;

    public PhotoDomain(Context context) {
        contentResolver = context.getContentResolver();
    }

    public List<PhotoEntity> getItems() {
        List<PhotoEntity> photos = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE}, null, null, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext()) {
            return photos;
        }
        cursor.moveToLast();
        PhotoEntity photoEntity;
        do {
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024 * 10) {
                photoEntity = new PhotoEntity();
                photoEntity.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                photos.add(photoEntity);
                if (PSManager.getInstance().contain(photoEntity)) {
                    photoEntity.isChecked = true;
                }
            }
        } while (cursor.moveToPrevious());
        return photos;
    }

    public List<PhotoEntity> getItems(String name) {
        List<PhotoEntity> photos = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE},
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = ?", new String[]{name}, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext()) {
           return photos;
        }
        cursor.moveToLast();
        PhotoEntity photoEntity;
        do {
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024 * 10) {
                photoEntity = new PhotoEntity();
                photoEntity.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                photos.add(photoEntity);
                if (PSManager.getInstance().contain(photoEntity)) {
                    photoEntity.isChecked = true;
                }
            }
        } while (cursor.moveToPrevious());
        return photos;
    }

}
