package com.orange1988.photoselector.base;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Orange on 15/11/29.
 */
public abstract class PSLoader<T> extends AsyncTaskLoader<List<T>> {

    protected IDomain domain;
    protected List<T> mItems;

    public PSLoader(Context context, IDomain domain) {
        super(context);
        this.domain = domain;
    }

    @Override
    public List<T> loadInBackground() {
        return domain.getItems();
    }

    @Override
    public void onCanceled(List<T> data) {
        super.onCanceled(data);
        onReleaseResource(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mItems != null) {
            onReleaseResource(mItems);
            mItems = null;
        }
    }

    @Override
    protected void onStartLoading() {
        if (mItems != null) {
            deliverResult(mItems);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    public void deliverResult(List<T> items) {
        if (isReset()) {
            if (mItems != null) {
                onReleaseResource(mItems);
            }
        }

        List<T> oldItems = items;
        mItems = items;
        if (isStarted()) {
            super.deliverResult(items);
        }
        if (oldItems != null) {
            onReleaseResource(oldItems);
        }
    }

    protected void onReleaseResource(List<T> mItems) {

    }
}
