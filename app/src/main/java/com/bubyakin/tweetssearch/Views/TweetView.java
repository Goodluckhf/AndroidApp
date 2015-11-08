package com.bubyakin.tweetssearch.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bubyakin.tweetssearch.models.Tweet;
import com.bubyakin.tweetssearch.R;

public class TweetView  extends RelativeLayout {

    private TextView _descriptionTxtView;

    private TextView _dateTxtView;

    private void setupChildren() {
        this._descriptionTxtView = (TextView) findViewById(R.id.itemDescription);
        this._dateTxtView = (TextView) findViewById(R.id.itemDate);
    }

    public static TweetView inflate(ViewGroup parent) {
        TweetView tweetView = (TweetView) LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_view, parent, false);

        return tweetView;
    }

    public TweetView(Context ctx) {
        this(ctx, null);
    }

    public TweetView(Context ctx, AttributeSet attrs) {
        this(ctx, attrs, 0);
    }

    public TweetView(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        LayoutInflater.from(ctx).inflate(R.layout.tweet_view_children, this, true);
        this.setupChildren();
    }

    public void setTweet(Tweet tweet) {
        this._descriptionTxtView.setText(tweet.getText());
        this._dateTxtView.setText(tweet.getDate().toString());
    }







}
