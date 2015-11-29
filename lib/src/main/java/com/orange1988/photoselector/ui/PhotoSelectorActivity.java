package com.orange1988.photoselector.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.adapter.FolderAdapter;
import com.orange1988.photoselector.adapter.PhotoAdapter;
import com.orange1988.photoselector.base.BaseActivity;
import com.orange1988.photoselector.core.FolderDomain;
import com.orange1988.photoselector.core.FolderLoader;
import com.orange1988.photoselector.core.PhotoDomain;
import com.orange1988.photoselector.core.PhotoLoader;
import com.orange1988.photoselector.entity.FolderEntity;
import com.orange1988.photoselector.entity.PhotoEntity;
import com.orange1988.photoselector.view.FolderItemView;
import com.orange1988.photoselector.view.PhotoItemView;

import java.util.List;

public class PhotoSelectorActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<List<PhotoEntity>>, PhotoItemView.IPhotoItem,View.OnClickListener{

    private PhotoLoader photoLoader;
    private PhotoDomain photoDomain;
    private PhotoAdapter photoAdapter;

    private FolderLoader folderLoader;
    private FolderDomain folderDomain;
    private FolderAdapter folderAdapter;


    private int max_selected_size = 0;

    public static final String KEY_MAX_SELECTED_SIZE = "KEY_MAX_SELECTED_SIZE";
    public static final String KEY_FOLDER_NAME = "KEY_FOLDER_NAME";

    private GridView photosGridView;
    private Button folderSelectorBtn;
    private ListView folderListView;
    private View folderListViewContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoDomain = new PhotoDomain(this);
        photoLoader = new PhotoLoader(this, photoDomain);
        folderDomain = new FolderDomain(this);
        folderLoader = new FolderLoader(this, folderDomain);
        initExtras();
        initViews();
        getSupportLoaderManager().initLoader(0, null, this);
        getSupportLoaderManager().initLoader(1, null, new FolderLoaderCallbacks());
    }

    private void initExtras() {

    }

    private void initViews() {
        folderSelectorBtn = (Button) findViewById(R.id.folder_selector_btn);
        folderSelectorBtn.setOnClickListener(this);
        folderSelectorBtn.setText(R.string.all_photos);
        photosGridView = (GridView) findViewById(R.id.photos_gv);
        photoAdapter = new PhotoAdapter(this, this);
        photosGridView.setAdapter(photoAdapter);
        folderListView = (ListView) findViewById(R.id.folder_lv);
        folderAdapter = new FolderAdapter(this, new OnFolderItemClickListener());
        folderListView.setAdapter(folderAdapter);
        folderListViewContainer = findViewById(R.id.folder_lv_container);
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
        photosGridView.smoothScrollToPosition(0);
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

    @Override
    public void onClick(View v) {
        if (v == folderSelectorBtn) {
            setFolderListViewVisible();
        }
    }

    private class FolderLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<FolderEntity>> {

        @Override
        public Loader<List<FolderEntity>> onCreateLoader(int id, Bundle args) {
            return folderLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<FolderEntity>> loader, List<FolderEntity> folders) {
            folderAdapter.setItems(folders);
            folderAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<FolderEntity>> loader) {

        }

    }

    private class OnFolderItemClickListener implements FolderItemView.IFolderItem {

        @Override
        public void onItemClickListener(FolderEntity folderEntity, int position) {
            folderAdapter.update(folderEntity);
            folderAdapter.notifyDataSetChanged();
            folderSelectorBtn.setText(folderEntity.name);
            setFolderListViewVisible();
            photoLoader.setFolderName(folderEntity.name);
            photoLoader.forceLoad();
        }
    }

    private void setFolderListViewVisible() {
        int visibility = folderListViewContainer.getVisibility();
        if (visibility == View.VISIBLE) {
            folderListViewContainer.setVisibility(View.GONE);
        } else if (visibility == View.GONE) {
            folderListViewContainer.setVisibility(View.VISIBLE);
        }
    }
}
