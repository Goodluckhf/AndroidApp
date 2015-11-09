package com.bubyakin.tweetssearch.network;

import android.os.AsyncTask;
import android.util.Log;

import com.bubyakin.tweetssearch.events.EventTrigger;
import com.bubyakin.tweetssearch.events.EventsContainer;
import com.bubyakin.tweetssearch.events.VoidArg;

public class RequestTask extends AsyncTask<Void, Void, Void> {
    private EventsContainer _events;

    public RequestTask() {
        super();
        _events = new EventsContainer();
        try {
            _events.register("before")
                   .register("after")
                   .register("process");
        }
        catch(Exception e) {
            Log.d("MyErrors", e.toString());
        }
    }

    public EventsContainer on(String event, EventTrigger callback) {
        try {
            this._events.on(event, callback);
        }
        catch(Exception e) {
            Log.d("MyErrors", e.toString());
        }
        return this._events;
    }

    @Override
    protected void onPreExecute() {
        try {
            this._events.trigger("before", new VoidArg());
        }
        catch (Exception e) {
            Log.d("MyErrors", e.toString());
        }
    }

    @Override
    protected Void doInBackground(Void... Params) {
        try {
            this._events.trigger("process", new VoidArg());
        }
        catch (Exception e) {
            Log.d("MyErrors", e.toString());
        }
        return (Void) null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            this._events.trigger("after", new VoidArg());
        }
        catch (Exception e) {
            Log.d("MyErrors", e.toString());
        }
    }
}
