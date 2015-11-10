package com.bubyakin.tweetssearch.network;

import android.os.AsyncTask;
import android.util.Log;

import com.bubyakin.tweetssearch.events.EventArg;
import com.bubyakin.tweetssearch.events.EventTrigger;
import com.bubyakin.tweetssearch.events.EventsContainer;
import com.bubyakin.tweetssearch.events.VoidArg;

public class RequestTask<T> extends AsyncTask<Void, Void, T> {
    private EventsContainer _events;

    public RequestTask() {
        super();
        _events = new EventsContainer();
        try {
            _events.register("before")
                   .register("after")
                   .register("process")
                   .register("cancel");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public EventsContainer on(String event, EventTrigger callback) {
        try {
            this._events.on(event, callback);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return this._events;
    }

    @Override
    protected void onPreExecute() {
        try {
            this._events.trigger("before", new VoidArg());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected T doInBackground(Void... Params) {
        try {
            this._events.trigger("process", new VoidArg());
            //if (isCancelled()) return null;
           // this.cancel(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(T data) {
        super.onPostExecute(data);
        try {
            this._events.trigger("after", (EventArg)data);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void onCancelled() {
        super.onCancelled();
        try {
            this._events.trigger("cancel", new VoidArg());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
