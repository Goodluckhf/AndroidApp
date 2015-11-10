package com.bubyakin.tweetssearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bubyakin.tweetssearch.models.Tweet;
import com.bubyakin.tweetssearch.network.TwitterDataProvider;
import com.bubyakin.tweetssearch.storage.StorageDataProvider;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;


public class SearchActivity extends Activity {
    Button btnSearch;
    TextView txtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        txtSearch = (TextView) findViewById(R.id.txtSearch);
        btnSearch.setOnClickListener((View v) -> {
            //ArrayList<Tweet> tweets = new ArrayList<Tweet>();
            String SearchValue = txtSearch.getEditableText().toString();
            if (SearchValue.isEmpty()) {
                return;
            }
            Tweet.getListByJSON(TwitterDataProvider.getInstance().getByHashtag(SearchValue));
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
}
