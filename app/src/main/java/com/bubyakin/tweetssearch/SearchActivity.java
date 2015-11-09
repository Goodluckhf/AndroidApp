package com.bubyakin.tweetssearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bubyakin.tweetssearch.models.Tweet;
import com.bubyakin.tweetssearch.network.TwitterDataProvider;
import com.bubyakin.tweetssearch.storage.StorageDataProvider;

import java.util.ArrayList;


public class SearchActivity extends Activity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StorageDataProvider.getInstance().open(getApplication());
        btn = (Button) findViewById(R.id.btnSearch);
        btn.setOnClickListener((View v) -> {
            ArrayList<Tweet> tweets = new ArrayList<Tweet>();
            tweets = Tweet.getListByJSON(TwitterDataProvider.getInstance().getByHashtag("киркоров"));

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        StorageDataProvider.getInstance().close();
    }
}
