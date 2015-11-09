package com.bubyakin.tweetssearch.events;


import org.json.JSONArray;
import org.json.JSONObject;

public class JSONArg implements EventArg {
    private JSONArray _data;

    public JSONArg(JSONArray object) {
        this._data = object;
    }


    public JSONArray get() {
        return this._data;
    }
}
