package com.orange1988.photoselector.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.adapter.PreviewPagerAdapter;
import com.orange1988.photoselector.base.BaseActivity;
import com.orange1988.photoselector.core.PhotoDomain;
import com.orange1988.photoselector.core.PhotoLoader;
import com.orange1988.photoselector.core.PhotoSelectedManager;
import com.orange1988.photoselector.entity.PhotoEntity;
import com.orange1988.photoselector.view.PreviewItemVIew;

import java.util.List;

/**
 *
 */
public class PhotoPreviewActivity extends BaseActivity implements PreviewItemVIew.IPreviewItem, ViewPager.OnPageChangeListener, LoaderManager.LoaderCallbacks<List<PhotoEntity>> {

    public static final String KEY_IS_PREVIEW = "KEY_IS_PREVIEW";
    public static final String KEY_FOLDER_NAME = "KEY_FOLDER_NAME";
    public static final String KEY_PHOTO_POSITION = "KEY_PHOTO_POSITION";

    private PhotoLoader photoLoader;
    private ViewPager viewPager;
    private PreviewPagerAdapter pagerAdapter;
    private Button selectBtn;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        boolean isPreview = getIntent().getBooleanExtra(KEY_IS_PREVIEW, false);
        if (isPreview) {
            List<PhotoEntity> list = PhotoSelectedManager.getInstance().copyPhotos();
            pagerAdapter.setItems(list);
            pagerAdapter.notifyDataSetChanged();
            updateBar(0);
        } else {
            String folderName = getIntent().getStringExtra(KEY_FOLDER_NAME);
            photoLoader = new PhotoLoader(this, new PhotoDomain(this));
            photoLoader.setFolderName(folderName);
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_preview;
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        selectBtn = (Button) findViewById(R.id.select_btn);
        pagerAdapter = new PreviewPagerAdapter(this, this);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public Loader<List<PhotoEntity>> onCreateLoader(int id, Bundle args) {
        return photoLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<PhotoEntity>> loader, List<PhotoEntity> photos) {
        int position = getIntent().getIntExtra(KEY_PHOTO_POSITION, 0);
        pagerAdapter.setItems(photos);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(position, false);
        updateBar(position);
    }

    @Override
    public void onLoaderReset(Loader<List<PhotoEntity>> loader) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateBar(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPhotoClickListener(PhotoEntity photo, int position) {

    }

    @Override
    public int getMaxSelectedSize() {
        return 0;
    }

    private void toggleBar() {

    }

    private void updateBar(int position) {
        toolbar.setTitle((position + 1) + "/" + pagerAdapter.getCount());
    }
}
