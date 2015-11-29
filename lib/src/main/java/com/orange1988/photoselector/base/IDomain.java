package com.orange1988.photoselector.base;

import android.content.ContentResolver;
import android.content.Context;

import java.util.List;

/**
 * Created by Ervin on 15/11/29.
 */
public interface  IDomain<T> {



    public abstract List<T> getItems();

}
