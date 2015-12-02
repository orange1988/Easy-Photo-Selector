package com.orange1988.photoselector.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.adapter.FolderAdapter;
import com.orange1988.photoselector.adapter.PhotoAdapter;
import com.orange1988.photoselector.base.BaseActivity;
import com.orange1988.photoselector.core.FolderDomain;
import com.orange1988.photoselector.core.FolderLoader;
import com.orange1988.photoselector.core.PhotoDomain;
import com.orange1988.photoselector.core.PhotoLoader;
import com.orange1988.photoselector.core.PhotoSelectedManager;
import com.orange1988.photoselector.entity.FolderEntity;
import com.orange1988.photoselector.entity.PhotoEntity;
import com.orange1988.photoselector.view.FolderItemView;
import com.orange1988.photoselector.view.PhotoItemView;

import java.io.File;
import java.util.List;

public class PhotoSelectorActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<List<PhotoEntity>>, PhotoItemView.IPhotoItem, View.OnClickListener {

    public static final String KEY_MAX_SELECTED_SIZE = "KEY_MAX_SELECTED_SIZE";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 200;

    public static final int REQ_CODE_PREVIEW = 1;
    public static final int REQ_CODE_ALL = 2;

    private PhotoLoader photoLoader;
    private PhotoAdapter photoAdapter;

    private FolderLoader folderLoader;
    private FolderAdapter folderAdapter;

    private int maxSelectedSize;
    private String folderName;

    private GridView photosGridView;
    private Button folderSelectorBtn;
    private Button previewBtn;
    private ListView folderListView;
    private View folderListViewContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoLoader = new PhotoLoader(this, new PhotoDomain(this));
        folderLoader = new FolderLoader(this, new FolderDomain(this));
        initExtras();
        initViews();
        getSupportLoaderManager().initLoader(0, null, this);
        getSupportLoaderManager().initLoader(1, null, new FolderLoaderCallbacks());
    }

    private void initExtras() {
        maxSelectedSize = getIntent().getIntExtra(KEY_MAX_SELECTED_SIZE, 9);
    }

    @Override
    protected void initViews() {
        super.initViews();
        folderSelectorBtn = (Button) findViewById(R.id.folder_selector_btn);
        folderSelectorBtn.setOnClickListener(this);
        folderSelectorBtn.setText(R.string.all_photos);
        previewBtn = (Button) findViewById(R.id.preview_btn);
        previewBtn.setOnClickListener(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    String path = getPhotoTakenFilePath();
                    if (!TextUtils.isEmpty(path)) {
                        PhotoEntity photo = new PhotoEntity();
                        photo.path = path;
                        notifyImageMedia(path);
                        getSupportLoaderManager().restartLoader(0, null, this);
//                        PhotoSelectedManager.getInstance().clear();
//                        PhotoSelectedManager.getInstance().addPhoto(photo);
//                        onCompleteBtnPressed();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
                break;
            case REQ_CODE_PREVIEW:
            case REQ_CODE_ALL:
                updatePhotosSelected();
                break;
            default:
                break;
        }
    }

    @Override
    public Loader<List<PhotoEntity>> onCreateLoader(int id, Bundle args) {
        return photoLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<PhotoEntity>> loader, List<PhotoEntity> data) {
        photoAdapter.setItems(data, isInAllPhotosFolder());
        photoAdapter.notifyDataSetChanged();
        photosGridView.smoothScrollToPosition(0);
        updateToolBarAndBottomBar();
    }

    @Override
    public void onLoaderReset(Loader<List<PhotoEntity>> loader) {

    }

    @Override
    public int getSelectedLimit() {
        return maxSelectedSize;
    }

    @Override
    public void beyondSelectedLimit() {
        Toast.makeText(this, String.format(getResources().getString(R.string.beyond_max_size), maxSelectedSize), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(PhotoEntity photoEntity, PhotoItemView view) {
        if (photoEntity.isChecked) {
            PhotoSelectedManager.getInstance().addPhoto(photoEntity);
        } else {
            PhotoSelectedManager.getInstance().remove(photoEntity);
        }
        updateToolBarAndBottomBar();
    }

    @Override
    public void onItemClickListener(PhotoEntity photoEntity, int position) {
        Intent intent = new Intent(this, PhotoPreviewActivity.class);
        intent.putExtra(PhotoPreviewActivity.KEY_FOLDER_NAME, folderName);
        intent.putExtra(PhotoPreviewActivity.KEY_PHOTO_POSITION, position);
        startActivityForResult(intent, REQ_CODE_ALL);
    }

    @Override
    public void onCameraItemClick() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = makePhotoTakenFileUri(); // create a file to save the image
        intent.putExtra("return_data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private Uri makePhotoTakenFileUri() {
        String fileName = System.currentTimeMillis() + ".jpg";
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            return Uri.fromFile(new File(fileName));
        }
    }

    private String getPhotoTakenFilePath() {
        String pathResult = null;
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA},
                null, null, MediaStore.Images.ImageColumns._ID + " DESC");
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            pathResult = cursor.getString(column_index);
            cursor.close();
        }
        return pathResult;
    }

    private void notifyImageMedia(String path) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(path));
        intent.setData(uri);
        sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == folderSelectorBtn) {
            setFolderListViewVisible();
        } else if (v == previewBtn) {
            preview();
        }
    }

    private void preview() {
        Intent intent = new Intent(this, PhotoPreviewActivity.class);
        intent.putExtra(PhotoPreviewActivity.KEY_IS_PREVIEW, true);
        startActivityForResult(intent, REQ_CODE_PREVIEW);
    }

    private void setFolderListViewVisible() {
        int visibility = folderListViewContainer.getVisibility();
        if (visibility == View.VISIBLE) {
            folderListViewContainer.setVisibility(View.GONE);
        } else if (visibility == View.GONE) {
            folderListViewContainer.setVisibility(View.VISIBLE);
        }
    }

    private void updatePhotosSelected() {
        for (PhotoEntity photo : photoLoader.getPhotos()) {
            if (PhotoSelectedManager.getInstance().contain(photo)) {
                photo.isChecked = true;
            } else {
                photo.isChecked = false;
            }
        }
        photoAdapter.notifyDataSetChanged();
        updateToolBarAndBottomBar();
    }

    private void updateToolBarAndBottomBar() {
        int currentSelectedSize = PhotoSelectedManager.getInstance().getPhotos().size();
        if (currentSelectedSize == 0) {
            completeBtn.setEnabled(false);
            completeBtn.setText(R.string.complete);
            previewBtn.setEnabled(false);
        } else {
            completeBtn.setEnabled(true);
            completeBtn.setText(currentSelectedSize + "/" + maxSelectedSize + " " + getResources().getString(R.string.complete));
            previewBtn.setEnabled(true);
        }
    }

    private boolean isInAllPhotosFolder() {
        if (!TextUtils.isEmpty(folderName) && !folderName.equals(getResources().getString(R.string.all_photos))) {
            return false;
        }
        return true;
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
            folderName = folderEntity.name;
            setFolderListViewVisible();
            photoLoader.setFolderName(folderEntity.name);
            photoLoader.forceLoad();
        }
    }

    @Override
    protected void onBackBtnPressed() {
        super.onBackBtnPressed();
        finish();
    }

    @Override
    protected void onCompleteBtnPressed() {
        super.onCompleteBtnPressed();
        setResult(RESULT_OK);
        finish();
    }
}
