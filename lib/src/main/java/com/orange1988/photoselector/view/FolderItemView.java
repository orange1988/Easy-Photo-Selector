package com.orange1988.photoselector.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange1988.photoselector.R;
import com.orange1988.photoselector.entity.FolderEntity;
import com.squareup.picasso.Picasso;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class FolderItemView extends LinearLayout implements View.OnClickListener {


    private ImageView imageView;
    private TextView titleTv;
    private TextView countTv;
    private ImageView checkView;

    private IFolderItem iFolderItem;
    private FolderEntity folderEntity;
    private int position;

    public FolderItemView(Context context) {
        super(context);
        inflate(context, R.layout.folder_item, this);
        imageView = (ImageView) findViewById(R.id.folder_image_iv);
        titleTv = (TextView) findViewById(R.id.folder_title_tv);
        countTv = (TextView) findViewById(R.id.folder_count_tv);
        checkView = (ImageView) findViewById(R.id.folder_cb);
        setOnClickListener(this);
    }


    @Override
    public void setSelected(boolean selected) {
        if (folderEntity == null) {
            return;
        }
        folderEntity.isChecked = selected;
        setBtnChecked(checkView, selected);
    }

    public void setData(FolderEntity folderEntity, int position, final IFolderItem iFolderItem) {
        this.folderEntity = folderEntity;
        this.position = position;
        this.iFolderItem = iFolderItem;
        this.loadImage(folderEntity.path);
        titleTv.setText(folderEntity.name);
        countTv.setText(String.format(getContext().getResources().getString(R.string.photos_size),folderEntity.size ));
        setBtnChecked(checkView, folderEntity.isChecked);
    }

    private void loadImage(String path) {
        Picasso.with(getContext()).load("file://" + path).resizeDimen(R.dimen.folder_item_image_size, R.dimen.folder_item_image_size).centerCrop().into(imageView);
    }

    @Override
    public void onClick(View v) {
        if (iFolderItem != null) {
            iFolderItem.onItemClickListener(folderEntity, position);
        }
    }

    public interface IFolderItem {
        void onItemClickListener(FolderEntity folderEntity, int position);
    }

    private void setBtnChecked(ImageView v, boolean isChecked) {
        if (v == null) {
            return;
        }
        int res = isChecked ? R.drawable.icon_checked : R.drawable.icon_unchecked;
        v.setImageResource(res);
    }
}
