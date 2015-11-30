package com.orange1988.photoselector.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
public class PhotoPreviewActivity extends BaseActivity implements View.OnClickListener, PreviewItemVIew.IPreviewItem, ViewPager.OnPageChangeListener, LoaderManager.LoaderCallbacks<List<PhotoEntity>> {

    public static final String KEY_IS_PREVIEW = "KEY_IS_PREVIEW";
    public static final String KEY_FOLDER_NAME = "KEY_FOLDER_NAME";
    public static final String KEY_PHOTO_POSITION = "KEY_PHOTO_POSITION";
    public static final String KEY_MAX_SELECTED_SIZE = "KEY_MAX_SELECTED_SIZE";

    private PhotoLoader photoLoader;
    private ViewPager viewPager;
    private PreviewPagerAdapter adapter;
    private Button completeBtn;
    private Toolbar toolbar;
    private View bottomBar;
    private View selectContainer;
    private ImageView selectCb;

    private int maxSelectedSize;
    private int currentSelectedSize;
    private int currentPosition;
    private PhotoEntity photo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        boolean isPreview = getIntent().getBooleanExtra(KEY_IS_PREVIEW, false);
        if (isPreview) {
            List<PhotoEntity> list = PhotoSelectedManager.getInstance().copyPhotos();
            maxSelectedSize = currentSelectedSize = list.size();
            adapter.setItems(list);
            adapter.notifyDataSetChanged();
            photo = adapter.getItem(0);
            currentPosition = 0;
            updateToolBar(currentPosition, currentSelectedSize);
            updateBottomBar(adapter.getItem(currentPosition).isChecked);
        } else {
            maxSelectedSize = getIntent().getIntExtra(KEY_MAX_SELECTED_SIZE, 9);
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
        bottomBar = findViewById(R.id.bottom_bar);
        completeBtn = (Button) findViewById(R.id.complete_btn);
        completeBtn.setOnClickListener(this);
        selectContainer = findViewById(R.id.select_container);
        selectContainer.setOnClickListener(this);
        selectCb = (ImageView) findViewById(R.id.select_cb);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new PreviewPagerAdapter(this, this);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == selectContainer) {
            checkImage();
        } else if (v == completeBtn) {
            finish();
        }
    }

    @Override
    public Loader<List<PhotoEntity>> onCreateLoader(int id, Bundle args) {
        return photoLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<PhotoEntity>> loader, List<PhotoEntity> photos) {
        int position = getIntent().getIntExtra(KEY_PHOTO_POSITION, 0);
        adapter.setItems(photos);
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(position, false);
        currentSelectedSize = PhotoSelectedManager.getInstance().getPhotos().size();
        updateToolBar(position, currentSelectedSize);
    }

    @Override
    public void onLoaderReset(Loader<List<PhotoEntity>> loader) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        photo = adapter.getItem(position);
        currentPosition = position;
        updateToolBar(position, currentSelectedSize);
        updateBottomBar(photo.isChecked);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPhotoClickListener(PhotoEntity photo, int position) {
        toggleBar();
    }

    @Override
    public int getMaxSelectedSize() {
        return maxSelectedSize;
    }

    private void toggleBar() {
        int visible = toolbar.getVisibility();
        if (visible == View.VISIBLE) {
            toolbar.setVisibility(View.GONE);
            bottomBar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            bottomBar.setVisibility(View.VISIBLE);
        }

    }

    private void updateToolBar(int position, int currentSelectedSize) {
        toolbar.setTitle((position + 1) + "/" + adapter.getCount());
        completeBtn.setText(currentSelectedSize + "/" + maxSelectedSize + " " + getResources().getString(R.string.complete));
    }

    private void updateBottomBar(boolean isChecked) {
        selectCb.setImageResource(isChecked ? R.drawable.icon_checked : R.drawable.icon_unchecked);
    }

    private void checkImage() {
        boolean isChecked = photo.isChecked;
        if (isChecked) {
            photo.isChecked = !isChecked;
            PhotoSelectedManager.getInstance().remove(photo);
        } else {
            photo.isChecked = !isChecked;
            PhotoSelectedManager.getInstance().addPhoto(photo);
        }
        currentSelectedSize = PhotoSelectedManager.getInstance().getPhotos().size();
        updateToolBar(currentPosition, currentSelectedSize);
        updateBottomBar(photo.isChecked);
    }

}
