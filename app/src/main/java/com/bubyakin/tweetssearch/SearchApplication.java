package com.bubyakin.tweetssearch;

import android.app.Application;

import com.bubyakin.tweetssearch.storage.StorageDataProvider;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class SearchApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StorageDataProvider.getInstance().open(getApplicationContext());

    }

}
