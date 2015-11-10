package com.bubyakin.tweetssearch.storage;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bubyakin.tweetssearch.events.EventTrigger;
import com.bubyakin.tweetssearch.events.EventsContainer;
import com.bubyakin.tweetssearch.events.TweetListArg;
import com.bubyakin.tweetssearch.events.VoidArg;
import com.bubyakin.tweetssearch.models.Tweet;
import com.bubyakin.tweetssearch.models.User;
import com.bubyakin.tweetssearch.network.RequestTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class StorageDataProvider {
    private static volatile StorageDataProvider _instance;
    private SQLiteDatabase _db;
    private DatabaseHelper _dbHelper;
    private Cursor _data = null;
    private EventsContainer _events;

    private StorageDataProvider() {
        this._events = new EventsContainer();
        try {
            this._events.register("tweetRecieve")
                        .register("userRecieve")
                        .register("cancel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StorageDataProvider getInstance() {
        if(_instance == null) {
            synchronized (StorageDataProvider.class) {
                if (_instance == null) {
                    _instance = new StorageDataProvider();
                }
            }
        }
        return _instance;
    }

    public StorageDataProvider on(String event, EventTrigger callback) {
        try {
            this._events.on(event, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public void open(Context ctx) {
        this._dbHelper = new DatabaseHelper(ctx);
        this._db = this._dbHelper.getWritableDatabase();
    }

    public void close() {
        if (this._dbHelper!=null) {
            this._dbHelper.close();
        }
    }

    private Long addUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put("name", user.getName());
        cv.put("friends_count", user.getFriendsCount());
        return this._db.insert("user", null, cv);
    }

    private Long addTweet(Tweet tweet) {
        ContentValues cv = new ContentValues();
        cv.put("date", tweet.getDate().toString());
        cv.put("text", tweet.getText());
        cv.put("user_id", tweet.getUserId());
        return this._db.insert("tweet", null, cv);
    }

    private void removeCache() {
        this._dbHelper.dropTweetTable(this._db);
        this._dbHelper.dropUserTable(this._db);
        this._dbHelper.creatTweetTable(this._db);
        this._dbHelper.creatUserTable(this._db);
    }

    private boolean hasCache() {
        String sql = "SELECT count(*) as cnt " +
                        "FROM tweet;";
        Cursor cursor = this._db.rawQuery(sql, null);
        cursor.moveToNext();
        int count = cursor.getInt(cursor.getColumnIndex("cnt"));
        return count > 0;
    }

    public void cache(JSONArray list) {
        RequestTask request = new RequestTask();
        try {
            request.on("process", (eventArgs) -> {
                this.removeCache();
                for (int i = 0; i < list.length(); i++) {
                    try {
                        JSONObject object = list.getJSONObject(i);
                        User user = User.getByJSON(object);
                        Tweet tweet = Tweet.getByJSON(object);
                        Long userId = this.addUser(user);
                        user.setId(userId);
                        tweet.setUserId(userId);
                        Long tweetId = this.addTweet(tweet);
                        tweet.setId(tweetId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.execute();
    }

    public void requestTweets() {
        RequestTask request = new RequestTask();
        try {

            request.on("process", (eventArgs) -> {
                String sql = "SELECT *" +
                        "FROM tweet;";
                Cursor cursor = this._db.rawQuery(sql, null);
                this._data = cursor;
            }).on("after", (eventArgs) -> {
                ArrayList<Tweet> tweets = new ArrayList<Tweet>();
                if (this._data != null) {
                    while (this._data.moveToNext()) {
                        tweets.add(Tweet.getByCursor(this._data));
                    }
                }
                try {
                    this._events.trigger("tweetRecieve", new TweetListArg(tweets));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        } catch (Exception e) {
            Log.d("lol", "asdasdasdasdasd");
            e.getMessage();
            e.printStackTrace();
        }
        request.execute();
    }

    public void requestUserByTweetId(long id) {
        RequestTask request = new RequestTask();
        try {
            request.on("process", (eventArgs) -> {
                User user = new User();
                /*this._data = this._db.rawQuery("SELECT * " +
                                "FROM user " +
                                "WHERE id = (SELECT user_id " +
                                "FROM tweet " +
                                "WHERE id = ?" +
                                ")",
                        new String[]{String.valueOf(id)});*/
                this._data = this._db.rawQuery("SELECT * " +
                                "FROM user " +
                                "WHERE id = ?",
                        new String[]{String.valueOf(id)});
                this._data.moveToNext();
            }).on("after", (eventArg) -> {
                try {
                    this._events.trigger("userRecieve", User.getByCursor(this._data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.execute();
    }
}
