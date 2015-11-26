package com.orange1988.photoselector.ui;

import android.os.Bundle;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.base.BaseActivity;

public class PhotoSelectorActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_selector;
    }
}
