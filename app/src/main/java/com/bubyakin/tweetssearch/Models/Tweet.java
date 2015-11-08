package com.bubyakin.tweetssearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Tweet {

    private Date _date;

    private String _text;

    public Tweet() {
        this(new Date(), "");
    }

    public Tweet(Date date, String text) {
        this._date = date;
        this._text = text;
    }

    public void setDate(Date date) {
        this._date = date;
    }

    public void setText(String text) {
        this._text = text;
    }

    public Date getDate() {
        return _date;
    }

    public String getText() {
        return _text;
    }

    public static Tweet getByJSON(JSONObject object) {
        Tweet tweet = new Tweet();
        try {
            tweet.setDate(new Date(object.getString("created_at")));
            tweet.setText(object.getString("text"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet> getListByJSON(JSONObject object) {
        if(object == null) {
            return null;
        }
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        try {
            JSONArray data = object.getJSONArray("statuses");
            for(int i = 0; i < data.length(); i++) {
                tweets.add(getByJSON(data.getJSONObject(i)));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return tweets;
    }

}
