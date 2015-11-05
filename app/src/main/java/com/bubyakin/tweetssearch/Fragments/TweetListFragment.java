package com.bubyakin.tweetssearch.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bubyakin.tweetssearch.Models.Tweet;
import com.bubyakin.tweetssearch.TweetsAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TweetListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for (int i = 0; i < 100; i++) {
            Date date = new Date();
            String text = String.format("Description of Item %d", i);
            Tweet tweet = new Tweet(date, text);
            tweets.add(tweet);
        }

        this.setListAdapter(new TweetsAdapter(this.getActivity(), tweets));

        return  view;
    }
}
