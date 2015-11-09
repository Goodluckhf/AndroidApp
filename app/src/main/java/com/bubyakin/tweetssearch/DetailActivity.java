package com.bubyakin.tweetssearch;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.bubyakin.tweetssearch.storage.StorageDataProvider;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        StorageDataProvider.getInstance().open(getApplication());
    }

    @Override
    protected void onStop() {
        super.onStop();
        StorageDataProvider.getInstance().close();
    }
}
