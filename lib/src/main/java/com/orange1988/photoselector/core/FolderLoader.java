package com.orange1988.photoselector.core;

import android.content.Context;

import com.orange1988.photoselector.base.PSLoader;
import com.orange1988.photoselector.base.IPSDomain;
import com.orange1988.photoselector.entity.PSFolderEntity;

/**
 * Created by Ervin on 15/11/29.
 */
public class FolderLoader extends PSLoader<PSFolderEntity> {

    public FolderLoader(Context context, IPSDomain domain) {
        super(context, domain);
    }
}
