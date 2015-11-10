package com.bubyakin.tweetssearch.models;


import android.database.Cursor;

import com.bubyakin.tweetssearch.events.EventArg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User  implements EventArg {
    private Long _id;
    private String _name;
    private Integer _friendsCount;
    private String _image;

    public String getName() {
        return _name;
    }

    public Integer getFriendsCount() {
        return _friendsCount;
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setId(Long id) {
        this._id = id;
    }

    public Long getId() {
        return this._id;
    }

    public void setFriendsCount(Integer friendsCount) {
        this._friendsCount = friendsCount;
    }

    public static User getByCursor(Cursor cursor) {
        User user = new User();
        user.setName(cursor.getString(cursor.getColumnIndex("name")));
        user.setFriendsCount(cursor.getInt(cursor.getColumnIndex("friends_count")));
        user.setId(cursor.getLong(cursor.getColumnIndex("id")));
        return user;
    }

    public static User getByJSON(JSONObject object) {
        if(object == null) {
            return null;
        }

        User user = new User();
        try {
            object = object.getJSONObject("user");
            user.setName(object.getString("name"));
            user.setFriendsCount(object.getInt("friends_count"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
