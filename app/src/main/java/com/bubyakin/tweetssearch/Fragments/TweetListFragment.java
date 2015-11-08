package com.bubyakin.tweetssearch.fragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bubyakin.tweetssearch.events.EventsContainer;
import com.bubyakin.tweetssearch.events.JSONArgs;
import com.bubyakin.tweetssearch.models.Tweet;
import com.bubyakin.tweetssearch.TweetsAdapter;
import com.bubyakin.tweetssearch.network.TwitterDataProvider;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;

public class TweetListFragment extends ListFragment {

    private TweetsAdapter _adapter;
    private ProgressDialog _dialog;

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);



        return  view;
    }*/

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this._dialog = new ProgressDialog(this.getActivity());
        this._dialog.setIndeterminate(true);
        this._dialog.setCancelable(false);
        this._dialog.setMessage("Loading...");
        this._adapter = new TweetsAdapter(this.getActivity(), new ArrayList<Tweet>());
        try {
            TwitterDataProvider.getInstance().on("recieveData", (arg) -> {
                JSONObject data = ((JSONArgs) arg).get();
                ArrayList<Tweet> tweets = new ArrayList<Tweet>();
                tweets = Tweet.getListByJSON(data);
                this._adapter.addAll(tweets);
                this._adapter.notifyDataSetChanged();
                this._dialog.dismiss();
            }).on("beforeSend", (arg) -> {
                this._adapter.clear();
                this._dialog.show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setListAdapter(this._adapter);
    }
}
