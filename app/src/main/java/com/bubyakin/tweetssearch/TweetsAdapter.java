package com.bubyakin.tweetssearch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bubyakin.tweetssearch.Models.Tweet;
import com.bubyakin.tweetssearch.Views.TweetView;

import java.util.List;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

    public TweetsAdapter(Context ctx, List<Tweet> tweets) {
        super(ctx, 0, tweets);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TweetView  tweetView = (TweetView)convertView;
        if(tweetView == null) {
            tweetView = tweetView.inflate(parent);
        }
        tweetView.setTweet(this.getItem(position));
        return tweetView;
    }
}
