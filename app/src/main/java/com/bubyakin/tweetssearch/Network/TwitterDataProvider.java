package com.bubyakin.tweetssearch.Network;

import android.util.Log;

import com.bubyakin.tweetssearch.Events.EventArgs;
import com.bubyakin.tweetssearch.Events.EventTrigger;
import com.bubyakin.tweetssearch.Events.EventsContainer;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TwitterDataProvider {

    private static volatile TwitterDataProvider _instance;

    private HttpURLConnection _connection;

    private EventsContainer _events;

    private JSONObject _data = null;

    private TwitterDataProvider() {
        _events = new EventsContainer();
        try {
            _events.register("beforeSend")
                   .register("afterSend");
        } catch (Exception e) {
            Log.d("MyErrors", e.toString());
        }
    }

    public static TwitterDataProvider getInstance() {
        if(_instance == null) {
            synchronized (TwitterDataProvider.class) {
                if (_instance == null) {
                    _instance = new TwitterDataProvider();
                }
            }
        }
        return _instance;
    }

    private void connect(String urlString) {
        try {
            URL url = new URL(urlString);
            this._connection = (HttpURLConnection) url.openConnection();
            this._connection.setRequestProperty("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAADiJRQAAAAAAt%2Brjl%2Bqmz0rcy%2BBbuXBBsrUHGEg%3Dq0EK2aWqQMb15gCZNwZo9yqae0hpe2FDsS92WAu0g");
            this._connection.setRequestProperty("Accept-Encoding", "identity");
            this._connection.setRequestProperty("Content-type", "text/html; charset=utf-8");

            this._connection.connect();
        } catch (Exception e) {
            Log.d("MyErrors", e.toString());
        } finally {
            this.disconnect();
        }
    }

    private void disconnect() {
        this._connection.disconnect();
        this._data = null;
    }

    private String getReaderData() {
        BufferedReader reader = null;
        try {
            InputStream stream = this._connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception e) {
            Log.d("MyErrors", this._connection.getErrorStream().toString());
            Log.d("MyErrors", this._connection.getHeaderFields().toString());
        } finally {
            this.disconnect();
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                Log.d("MyErrors", e.toString());
            }
        }
        return null;
    }

    public EventsContainer on(String event, EventTrigger callback) {
        try {
            this._events.on(event, callback);
        } catch (Exception e) {
            Log.d("MyErrors", e.toString());
        }
        return this._events;
    }

    public JSONObject getByHashtag(String q) {
        String urlString = "http://api.twitter.com/1.1/search/tweets.json?q=%23" + q + "&count=15&include_entities=false";
        RequestTask request = new RequestTask();
        try {
            request.on("before", (EventArgs v) -> {
                try {
                    this._events.trigger("beforeSend", v);
                } catch (Exception e) {
                    Log.d("MyErrors", e.toString());
                }
            }).on("process", (EventArgs v) -> {
                try {
                    this.connect(urlString);

                    this._data = new JSONObject(this.getReaderData());
                } catch (Exception e) {
                    Log.d("MyErrors", e.toString());
                    this.disconnect();
                }
            }).on("after", (EventArgs v) -> {
                try {
                    this._events.trigger("afterSend", v);
                } catch (Exception e) {
                    Log.d("MyErrors", e.toString());
                }
            });
            request.execute();
        } catch (Exception e) {
            Log.d("MyErrors", e.toString());
        }
        return this._data;
    }
}
