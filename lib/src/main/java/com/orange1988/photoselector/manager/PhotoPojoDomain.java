package com.orange1988.photoselector.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.orange1988.photoselector.entity.PhotoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PhotoPojoDomain {

    private ContentResolver contentResolver;

    public PhotoPojoDomain(Context context) {
        contentResolver = context.getContentResolver();
    }

    public List<PhotoEntity> getLatestPhotos() {
        List<PhotoEntity> photos = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE}, null, null, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext()) {
            return photos;
        }
        cursor.moveToLast();
        PhotoEntity photoPojo;
        do {
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024 * 10) {
                photoPojo = new PhotoEntity();
                photoPojo.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                photos.add(photoPojo);
            }
        } while (cursor.moveToPrevious());
        return photos;
    }

    public List<PhotoEntity> getPhotos(String name) {
        List<PhotoEntity> photos = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE},
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = ?", new String[]{name}, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext()) {
           return photos;
        }
        cursor.moveToLast();
        PhotoEntity photoPojo;
        do {
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024 * 10) {
                photoPojo = new PhotoEntity();
                photoPojo.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                photos.add(photoPojo);
            }
        } while (cursor.moveToPrevious());
        return photos;
    }

}
