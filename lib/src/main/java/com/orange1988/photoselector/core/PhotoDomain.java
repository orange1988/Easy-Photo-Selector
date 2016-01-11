package com.orange1988.photoselector.core;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.orange1988.photoselector.base.IPSDomain;
import com.orange1988.photoselector.entity.PSPhotoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class PhotoDomain implements IPSDomain<PSPhotoEntity> {

    private ContentResolver contentResolver;

    public PhotoDomain(Context context) {
        contentResolver = context.getContentResolver();
    }

    public List<PSPhotoEntity> getItems() {
        List<PSPhotoEntity> photos = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE}, null, null, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext()) {
            return photos;
        }
        cursor.moveToLast();
        PSPhotoEntity photoEntity;
        do {
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024 * 10) {
                photoEntity = new PSPhotoEntity();
                photoEntity.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                photos.add(photoEntity);
                if (PSManager.getInstance().contain(photoEntity)) {
                    photoEntity.isChecked = true;
                }
            }
        } while (cursor.moveToPrevious());
        return photos;
    }

    public List<PSPhotoEntity> getItems(String name) {
        List<PSPhotoEntity> photos = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE},
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = ?", new String[]{name}, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext()) {
           return photos;
        }
        cursor.moveToLast();
        PSPhotoEntity photoEntity;
        do {
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024 * 10) {
                photoEntity = new PSPhotoEntity();
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
