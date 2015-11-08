package com.bubyakin.tweetssearch.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EventsContainer {
    private Map<String, ArrayList<EventTrigger>> _events = new HashMap<String, ArrayList<EventTrigger>>();

    public boolean has(String event) {
        return this._events.containsKey(event);
    }

    public EventsContainer register(String event) throws Exception {
        if(!this.has(event)) {
            this._events.put(event, new ArrayList<EventTrigger>());
        }
        else {
            throw new Exception("Error! already has event");
        }
        return this;
    }

    public EventsContainer on(String event, EventTrigger callback) throws Exception {
        if(this.has(event)) {
            this._events.get(event).add(callback);
        }
        else {
            throw new Exception("Error! already has event");
        }
        return this;
    }

    public void trigger(String event, EventArgs data) throws Exception {
        if(this.has(event)) {
            ArrayList<EventTrigger> list = this._events.get(event);
            for(int i = 0; i < list.size(); i++) {
                list.get(i).callback(data);
            }
        }
        else {
            throw new Exception("Error! already has event");
        }
    }
    public void trigger(String event) throws Exception {
        if(this.has(event)) {
            ArrayList<EventTrigger> list = this._events.get(event);
            for(int i = 0; i < list.size(); i++) {
                list.get(i).callback(null);
            }
        }
        else {
            throw new Exception("Error! already has event");
        }
    }

}
