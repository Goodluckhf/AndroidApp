package com.bubyakin.tweetssearch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bubyakin.tweetssearch.models.User;
import com.bubyakin.tweetssearch.storage.StorageDataProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class DetailActivity extends Activity {
    private ImageView _imageView;
    private TextView  _name;
    private TextView  _friendsCountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this._imageView = (ImageView) findViewById(R.id.imageView);
        this._name = (TextView) findViewById(R.id.txtDescriptionName);
        this._friendsCountView = (TextView) findViewById(R.id.txtDescriptionFriendsCount);
        StorageDataProvider.getInstance().on("userRecieve", (userObj) -> {
            User user = (User) userObj;
            this._name.setText(user.getName());
            this._friendsCountView.setText(user.getFriendsCount().toString());
            Glide.with(this.getApplication())
                    .load(user.getImageUri())
                    .placeholder(R.drawable.profile_place_holder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(_imageView);

        });
        StorageDataProvider.getInstance().requestUserByTweetId(getIntent().getLongExtra("id", new Long(1)));
    }

}
