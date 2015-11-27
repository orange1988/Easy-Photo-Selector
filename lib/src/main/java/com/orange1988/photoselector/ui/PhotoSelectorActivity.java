package com.orange1988.photoselector.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.GridView;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.base.BaseActivity;
import com.orange1988.photoselector.manager.PhotoLoader;
import com.orange1988.photoselector.manager.PhotoPojoDomain;
import com.orange1988.photoselector.pojo.PhotoPojo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoSelectorActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<List<PhotoPojo>> {

    private PhotoLoader photoLoader;
    private PhotoPojoDomain photoPojoDomain;

    public static final String KEY_FOLDER_NAME = "KEY_FOLDER_NAME";

    private GridView photosGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoPojoDomain = new PhotoPojoDomain(this);
        photoLoader = new PhotoLoader(this, photoPojoDomain);
        initViews();
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void initViews() {
        photosGridView = (GridView) findViewById(R.id.photos_gv);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_selector;
    }

    @Override
    public Loader<List<PhotoPojo>> onCreateLoader(int id, Bundle args) {
        return photoLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<PhotoPojo>> loader, List<PhotoPojo> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<PhotoPojo>> loader) {

    }

    private Bundle generateBundleWithAlbumName(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FOLDER_NAME, name);
        return bundle;
    }
}
