package com.bubyakin.tweetssearch.models;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Tweet {
    private Long _id;

    private Long _userId;

    private Date _date;

    private String _text;

    public Tweet() {
        this(new Date(), "");
    }

    public Tweet(Date date, String text) {
        this._date = date;
        this._text = text;
    }

    public void setId(Long id) {
        this._id = id;
    }

    public Long getId() {
        return this._id;
    }

    public Long getUserId() {
        return this._userId;
    }

    public void setUserId(Long userId) {
        this._userId = userId;
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

    public static ArrayList<Tweet> getListByJSON(JSONArray list) {
        if(list == null) {
            return null;
        }
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        try {
            for(int i = 0; i < list.length(); i++) {
                tweets.add(getByJSON(list.getJSONObject(i)));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return tweets;
    }

    public static Tweet getByCursor(Cursor tweetCursor) {
        Tweet tweet = new Tweet();
        tweet.setText(tweetCursor.getString(tweetCursor.getColumnIndex("text")));
        tweet.setDate(new Date(tweetCursor.getString(tweetCursor.getColumnIndex("date"))));
        tweet.setId(tweetCursor.getLong(tweetCursor.getColumnIndex("id")));
        return tweet;
    }

}
