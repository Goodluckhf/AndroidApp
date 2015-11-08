package com.bubyakin.tweetssearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bubyakin.tweetssearch.Events.EventArgs;
import com.bubyakin.tweetssearch.Events.VoidArgs;
import com.bubyakin.tweetssearch.Network.TwitterDataProvider;


public class SearchActivity extends Activity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        try {
            TwitterDataProvider.getInstance().on("beforeSend", (EventArgs v) -> {
                Log.d("MyErrors", "START");
            }).on("afterSend", (EventArgs v) -> {
                Log.d("MyErrors", "FINISH");
            });
        } catch (Exception e) {
            Log.d("MyErrors", e.toString());
        }
        btn = (Button) findViewById(R.id.btnSearch);
        btn.setOnClickListener((View v) -> {
            //ОШИБКА
            TwitterDataProvider.getInstance().getByHashtag("Киркоров");
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
