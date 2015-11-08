package com.bubyakin.tweetssearch.events;


import org.json.JSONArray;
import org.json.JSONObject;

public class JSONArgs implements EventArgs{
    private JSONObject _data;

    public JSONArgs(JSONObject object) {
        this._data = object;
    }


    public JSONObject get() {
        return this._data;
    }
}
