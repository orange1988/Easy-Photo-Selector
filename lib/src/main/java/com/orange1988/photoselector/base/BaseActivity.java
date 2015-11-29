package com.orange1988.photoselector.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();
}
