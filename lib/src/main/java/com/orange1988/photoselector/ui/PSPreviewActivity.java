package com.orange1988.photoselector.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.adapter.PSPreviewPagerAdapter;
import com.orange1988.photoselector.base.PSBaseActivity;
import com.orange1988.photoselector.core.PhotoDomain;
import com.orange1988.photoselector.core.PhotoLoader;
import com.orange1988.photoselector.core.PSManager;
import com.orange1988.photoselector.entity.PhotoEntity;
import com.orange1988.photoselector.view.PreviewItemVIew;

import java.util.List;

/**
 *
 */
public class PSPreviewActivity extends PSBaseActivity implements View.OnClickListener, PreviewItemVIew.IPreviewItem, ViewPager.OnPageChangeListener, LoaderManager.LoaderCallbacks<List<PhotoEntity>> {

    public static final String KEY_IS_PREVIEW = "KEY_IS_PREVIEW";
    public static final String KEY_FOLDER_NAME = "KEY_FOLDER_NAME";
    public static final String KEY_PHOTO_POSITION = "KEY_PHOTO_POSITION";
    public static final String KEY_MAX_SELECTED_SIZE = "KEY_MAX_SELECTED_SIZE";

    private PhotoLoader photoLoader;
    private ViewPager viewPager;
    private PSPreviewPagerAdapter adapter;
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
            List<PhotoEntity> list = PSManager.getInstance().copyPhotos();
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

    @Override
    protected void initViews() {
        super.initViews();
        bottomBar = findViewById(R.id.bottom_bar);
        selectContainer = findViewById(R.id.select_container);
        selectContainer.setOnClickListener(this);
        selectCb = (ImageView) findViewById(R.id.select_cb);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new PSPreviewPagerAdapter(this, this);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == selectContainer) {
            checkImage();
        }
    }

    @Override
    public Loader<List<PhotoEntity>> onCreateLoader(int id, Bundle args) {
        return photoLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<PhotoEntity>> loader, List<PhotoEntity> photos) {
        int position = getIntent().getIntExtra(KEY_PHOTO_POSITION, 0);
        String folderName = getIntent().getStringExtra(KEY_FOLDER_NAME);
        if (TextUtils.isEmpty(folderName) || folderName.equals(getResources().getString(R.string.all_photos))) {
            position = position - 1;
        }
        adapter.setItems(photos);
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(position, false);
        currentSelectedSize = PSManager.getInstance().getPhotos().size();
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
        titleView.setText((position + 1) + "/" + adapter.getCount());
        completeBtn.setText(currentSelectedSize + "/" + maxSelectedSize + " " + getResources().getString(R.string.complete));
    }

    private void updateBottomBar(boolean isChecked) {
        selectCb.setImageResource(isChecked ? R.drawable.icon_checked : R.drawable.icon_unchecked);
    }

    private void checkImage() {

        boolean isChecked = photo.isChecked;
        if (isChecked) {
            photo.isChecked = !isChecked;
            PSManager.getInstance().remove(photo);
        } else {
            if (PSManager.getInstance().getPhotos().size() >= maxSelectedSize) {
                Toast.makeText(this, String.format(getResources().getString(R.string.beyond_max_size), maxSelectedSize), Toast.LENGTH_SHORT).show();
                return;
            }
            photo.isChecked = !isChecked;
            PSManager.getInstance().addPhoto(photo);
        }
        currentSelectedSize = PSManager.getInstance().getPhotos().size();
        updateToolBar(currentPosition, currentSelectedSize);
        updateBottomBar(photo.isChecked);
    }

    private void quitActivity() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onBackBtnPressed() {
        super.onBackBtnPressed();
        quitActivity();
    }

    @Override
    protected void onCompleteBtnPressed() {
        super.onCompleteBtnPressed();
    }
}
