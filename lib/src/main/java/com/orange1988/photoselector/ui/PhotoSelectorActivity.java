package com.orange1988.photoselector.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.GridView;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.adapter.PhotoAdapter;
import com.orange1988.photoselector.base.BaseActivity;
import com.orange1988.photoselector.manager.PhotoLoader;
import com.orange1988.photoselector.manager.PhotoPojoDomain;
import com.orange1988.photoselector.entity.PhotoEntity;
import com.orange1988.photoselector.view.PhotoItemView;

import java.util.List;

public class PhotoSelectorActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<List<PhotoEntity>>,PhotoItemView.IPhotoItemView {

    private PhotoLoader photoLoader;
    private PhotoPojoDomain photoPojoDomain;
    private PhotoAdapter photoAdapter;

    private int max_selected_size = 0;

    public static final String KEY_MAX_SELECTED_SIZE = "KEY_MAX_SELECTED_SIZE";
    public static final String KEY_FOLDER_NAME = "KEY_FOLDER_NAME";

    private GridView photosGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoPojoDomain = new PhotoPojoDomain(this);
        photoLoader = new PhotoLoader(this, photoPojoDomain);
        initViews();
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void initViews() {
        photosGridView = (GridView) findViewById(R.id.photos_gv);
        photoAdapter = new PhotoAdapter(this, this);
        photosGridView.setAdapter(photoAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_selector;
    }

    @Override
    public Loader<List<PhotoEntity>> onCreateLoader(int id, Bundle args) {
        return photoLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<PhotoEntity>> loader, List<PhotoEntity> data) {
        photoAdapter.setItems(data);
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<PhotoEntity>> loader) {

    }

    private Bundle generateBundleWithAlbumName(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FOLDER_NAME, name);
        return bundle;
    }

    @Override
    public int getSelectedLimit() {
        return 0;
    }

    @Override
    public void beyondSelectedLimit() {
        //TODO 给予提示
    }

    @Override
    public void onCheckedChanged(PhotoEntity photoEntity, PhotoItemView view, boolean isChecked) {

    }

    @Override
    public void onItemClickListener(PhotoEntity photoEntity, int position) {

    }
}
