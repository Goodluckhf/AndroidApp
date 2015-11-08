package com.bubyakin.tweetssearch.models;


public class User {
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

    public void setFriendsCount(Integer friendsCount) {
        this._friendsCount = friendsCount;
    }
}
