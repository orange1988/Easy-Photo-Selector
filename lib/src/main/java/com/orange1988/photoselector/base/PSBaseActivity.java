package com.orange1988.photoselector.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orange1988.photoselector.R;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public abstract class PSBaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected Button completeBtn;
    protected TextView titleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initViews();
    }

    protected abstract int getLayoutId();

    protected void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackBtnPressed();
            }
        });
        completeBtn = (Button) findViewById(R.id.complete_btn);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCompleteBtnPressed();
            }
        });
        titleView = (TextView) findViewById(R.id.title_tv);
    }

    protected void onBackBtnPressed() {

    }

    protected void onCompleteBtnPressed() {

    }

    @Override
    public void onBackPressed() {
        onBackBtnPressed();
        super.onBackPressed();
    }
}
