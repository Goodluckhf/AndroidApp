package com.bubyakin.tweetssearch.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.bubyakin.tweetssearch.Events.EventTrigger;
import com.bubyakin.tweetssearch.Events.EventsContainer;
import com.bubyakin.tweetssearch.Events.VoidArgs;

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
            this._events.trigger("before", new VoidArgs());
        }
        catch (Exception e) {
            Log.d("MyErrors", e.toString());
        }
    }

    @Override
    protected Void doInBackground(Void... Params) {
        try {
            this._events.trigger("process", new VoidArgs());
        }
        catch (Exception e) {
            Log.d("MyErrors", e.toString());
        }
        return (Void) null;
    }

    protected void onPostExecute() {
        try {
            this._events.trigger("after", new VoidArgs());
        }
        catch (Exception e) {
            Log.d("MyErrors", e.toString());
        }
    }
}