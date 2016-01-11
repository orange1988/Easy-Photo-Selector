package com.orange1988.photoselector.core;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.base.IPSDomain;
import com.orange1988.photoselector.entity.PSFolderEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Orange on 15/11/29.
 */
public class FolderDomain implements IPSDomain<PSFolderEntity> {

    protected ContentResolver contentResolver;

    protected Context mContext;

    public FolderDomain(Context context) {
        contentResolver = context.getContentResolver();
        mContext = context;
    }

    /** 获取所有相册列表 */
    public List<PSFolderEntity> getItems() {
        List<PSFolderEntity> folders = new ArrayList<>();
        Map<String, PSFolderEntity> map = new HashMap<String, PSFolderEntity>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.SIZE }, null, null, null);
        if (cursor == null || !cursor.moveToNext())
            return folders;
        cursor.moveToLast();
        PSFolderEntity current = new PSFolderEntity(mContext.getResources().getString(R.string.all_photos), 0, cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)), true); // "最近照片"相册
        folders.add(current);
        do {
            if (cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) < 1024 * 10)
                continue;
            current.increaseSize();
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            if (map.keySet().contains(name))
                map.get(name).increaseSize();
            else {
                PSFolderEntity album = new PSFolderEntity(name, 1, cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)), false);
                map.put(name, album);
                folders.add(album);
            }
        } while (cursor.moveToPrevious());
        return folders;
    }

}
