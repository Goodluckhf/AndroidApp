package com.bubyakin.tweetssearch.fragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.bubyakin.tweetssearch.events.JSONArg;
import com.bubyakin.tweetssearch.events.TweetListArg;
import com.bubyakin.tweetssearch.models.Tweet;
import com.bubyakin.tweetssearch.TweetsAdapter;
import com.bubyakin.tweetssearch.network.TwitterDataProvider;
import com.bubyakin.tweetssearch.storage.StorageDataProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
        this._adapter = new TweetsAdapter(this.getActivity(), new ArrayList<Tweet>());
        this._adapter.clear();
        StorageDataProvider.getInstance().on("tweetRecieve", (tweetsArg) -> {
            ArrayList<Tweet> tweets = ((TweetListArg) tweetsArg).getTweets();
            this._adapter.addAll(tweets);
            this._adapter.notifyDataSetChanged();
            //this._dialog.dismiss();
        });
        this._dialog = new ProgressDialog(this.getActivity());
        this._dialog.setIndeterminate(true);
        this._dialog.setCancelable(false);
        this._dialog.setMessage("Loading...");
        //this._dialog.show();
        StorageDataProvider.getInstance().requestTweets();
        try {
            TwitterDataProvider.getInstance().on("recieveData", (arg) -> {
                JSONArray data = ((JSONArg) arg).get();
                StorageDataProvider.getInstance().cache(data);
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
