# Android-Photo-Selector

This is a photo selector like WeChat's photo selector. You can modify this in order to make it be more suitable for your own repository. This lib depends on picasso. I hope you like it because this repository can you develop easily.</br>
这个是一个像微信的图片选择器的一个图片选择器。你可以修改这个Repository，然后修改成你想要的样式。这个工程依赖Picasso，并采取Toolbar的方式。希望你能喜欢，让你的开发更方便。

Before you use it, make sure that you have declared activity and permission in AndroidManifest.xml.</br>
在使用之前，请确保你正确依赖此工程，并在AndroidManifest.xml声明activity和premission.

    <activity android:name="com.orange1988.photoselector.ui.PhotoSelectorActivity"
              android:label="@string/title_photo_selector_activity"
              android:theme="@style/AppTheme.NoActionBar"/>
    <activity android:name="com.orange1988.photoselector.ui.PhotoPreviewActivity"
            android:theme="@style/AppTheme.NoActionBar"/>
              
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-feature android:name="android.hardware.camera" />
    
Start the activity, and you can get photo list depending on resultCode in the method "onActivityResult", then you can display them in your app.</br>
启动Activity，并在onActivityResult中更具resultCode的结果得到你想要的相册列表，你可以在此展示你选中的列表。
  
    startActivityForResult(new Intent(MainActivity.this, PhotoSelectorActivity.class), 0);
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.setItems(PhotoSelectedManager.getInstance().getPhotos());
        adapter.notifyDataSetChanged();
    }

*You can select photos and preview them* </br>
*你可以选择图片并预览他们*

![](https://github.com/orange1988/Android-Photo-Selector/raw/master/gif/test1.gif)

*You can choose other folders and in the preview page you can check it or uncheck it*</br>
*你可以选择不同的图片，并在预览页面里选中或者取消*

![](https://github.com/orange1988/Android-Photo-Selector/raw/master/gif/test2.gif)

*You can preview all photos in the current folders.*</br>
*你可以预览当前相册的所有照片*

![](https://github.com/orange1988/Android-Photo-Selector/raw/master/gif/test3.gif)

*You can take a photo*</br>
*你可以照相，选中之后完成，再次进入是有记录的*

![](https://github.com/orange1988/Android-Photo-Selector/raw/master/gif/test4.gif)
