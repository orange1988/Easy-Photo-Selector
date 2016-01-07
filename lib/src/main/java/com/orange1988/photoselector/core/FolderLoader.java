package com.orange1988.photoselector.core;

import android.content.Context;

import com.orange1988.photoselector.base.PSLoader;
import com.orange1988.photoselector.base.IDomain;
import com.orange1988.photoselector.entity.FolderEntity;

/**
 * Created by Ervin on 15/11/29.
 */
public class FolderLoader extends PSLoader<FolderEntity> {

    public FolderLoader(Context context, IDomain domain) {
        super(context, domain);
    }
}
