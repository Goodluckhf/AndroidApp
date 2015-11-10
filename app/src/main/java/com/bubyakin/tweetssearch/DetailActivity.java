package com.bubyakin.tweetssearch;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bubyakin.tweetssearch.models.User;
import com.bubyakin.tweetssearch.storage.StorageDataProvider;


public class DetailActivity extends ActionBarActivity {
    private ImageView _imageView;
    private TextView  _name;
    private TextView  _friendsCountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //StorageDataProvider.getInstance().open(getApplication());
        this._imageView = (ImageView) findViewById(R.id.imageView);
        this._name = (TextView) findViewById(R.id.txtDescriptionName);
        this._friendsCountView = (TextView) findViewById(R.id.txtDescriptionFriendsCount);
        StorageDataProvider.getInstance().on("userRecieve", (userObj) -> {
            User user = (User) userObj;
            this._name.setText(user.getName());
            this._friendsCountView.setText(user.getFriendsCount().toString());

        });
        StorageDataProvider.getInstance().requestUserByTweetId(getIntent().getLongExtra("id", new Long(1)));
    }

}
