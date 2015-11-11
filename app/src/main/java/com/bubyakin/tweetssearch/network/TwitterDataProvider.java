package com.bubyakin.tweetssearch.network;

import android.util.Log;

import com.bubyakin.tweetssearch.events.EventArg;
import com.bubyakin.tweetssearch.events.EventTrigger;
import com.bubyakin.tweetssearch.events.EventsContainer;
import com.bubyakin.tweetssearch.events.JSONArg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;


public class TwitterDataProvider {

    private static volatile TwitterDataProvider _instance;

    private HttpsURLConnection _connection;

    private EventsContainer _events;

    private JSONArray _data = null;

    private TwitterDataProvider() {
        _events = new EventsContainer();
        try {
            _events.register("beforeSend")
                   .register("recieveData");
        } catch (Exception e) {
            e.printStackTrace();
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
            this._connection = (HttpsURLConnection) url.openConnection();
            this._connection.setRequestProperty("Authorization", " Bearer AAAAAAAAAAAAAAAAAAAAADiJRQAAAAAAt%2Brjl%2Bqmz0rcy%2BBbuXBBsrUHGEg%3Dq0EK2aWqQMb15gCZNwZo9yqae0hpe2FDsS92WAu0g");
            this._connection.addRequestProperty("Content-Type", "application/json; charset=utf-8");
            if (this._connection.getResponseCode() != 200) {
                InputStream stream =  this._connection.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                throw new RuntimeException("Failed : HTTP error code : "
                        + this._connection.getResponseCode() + "/n" + buffer);
            }
            this._connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public EventsContainer on(String event, EventTrigger callback) {
        try {
            this._events.on(event, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this._events;
    }

    public JSONArray getByHashtag(String q) {
        String urlString;
        try {
            urlString = "https://api.twitter.com/1.1/search/tweets.json?q=%23" + URLEncoder.encode(q, "UTF-8") + "&count=100";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported", e);
        }
        RequestTask request = new RequestTask();
        try {
            request.on("before", (EventArg v) -> {
                try {
                    this._events.trigger("beforeSend", v);
                } catch (Exception e) {
                    Log.d("MyErrors", e.toString());
                }
            }).on("process", (EventArg v) -> {
                try {
                    this.connect(urlString);
                    JSONObject parent = new JSONObject(this.getReaderData());
                    this._data = parent.getJSONArray("statuses");
                } catch (Exception e) {
                    Log.d("MyErrors", e.toString());
                    this.disconnect();
                }
            }).on("after", (EventArg v) -> {
                try {

                    this._events.trigger("recieveData", new JSONArg(this._data));
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
