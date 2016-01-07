package com.orange1988.photoselector.adapter;

import android.content.Context;
import android.view.View;

import com.orange1988.photoselector.base.PSBaseListAdapter;
import com.orange1988.photoselector.base.PSViewHolder;
import com.orange1988.photoselector.entity.FolderEntity;
import com.orange1988.photoselector.view.FolderItemView;

/**
 * Created by Mr. Orange on 15/11/26.
 */
public class FolderAdapter extends PSBaseListAdapter<FolderEntity> {

    private FolderItemView.IFolderItem iFolderItem;

    public FolderAdapter(Context context, FolderItemView.IFolderItem iFolderItem) {
        super(context);
        this.iFolderItem = iFolderItem;
    }

    @Override
    public void render(int position, PSViewHolder baseHolder, FolderEntity item) {
        if (baseHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) baseHolder;
            holder.folderItemView.setData(item, position, iFolderItem);
        }
    }

    @Override
    public PSViewHolder getViewHolder(int position, View convertView) {
        return new ViewHolder( convertView);
    }

    @Override
    public View getConvertView(int position) {
        return new FolderItemView(mContext);
    }

    public void update(FolderEntity folderEntity) {
        for (FolderEntity folder : items) {
            if (folder.equals(folderEntity)) {
                folder.isChecked = true;
            } else {
                folder.isChecked = false;
            }
        }
    }

    private static class ViewHolder extends PSViewHolder {

        public FolderItemView folderItemView;

        public ViewHolder(View view) {
            super(view);
            this.folderItemView = (FolderItemView) view;
        }
    }
}
