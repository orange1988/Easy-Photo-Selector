package com.orange1988.photoselector.base;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public abstract class BaseViewHolder {

    public BaseViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
