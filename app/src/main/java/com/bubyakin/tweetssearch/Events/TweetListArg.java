package com.bubyakin.tweetssearch.events;


import com.bubyakin.tweetssearch.models.Tweet;

import java.util.ArrayList;

public class TweetListArg implements EventArg{
    private ArrayList<Tweet> _tweets;

    public TweetListArg(ArrayList<Tweet> tweets) {
        this._tweets = tweets;
    }

    public ArrayList<Tweet> getTweets() {
        return this._tweets;
    }
}
