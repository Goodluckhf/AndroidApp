package com.bubyakin.tweetssearch;

import android.app.Application;

import com.bubyakin.tweetssearch.storage.StorageDataProvider;


public class SearchApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StorageDataProvider.getInstance().open(getApplicationContext());

    }

}
