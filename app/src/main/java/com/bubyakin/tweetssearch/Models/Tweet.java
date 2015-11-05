package com.bubyakin.tweetssearch.Models;

import java.util.Date;

public class Tweet {

    private Date _date;

    private String _text;

    public Tweet(Date date, String text) {
        this._date = date;
        this._text = text;
    }

    public void setDate(Date date) {
        this._date = date;
    }

    public void setText(String text) {
        this._text = text;
    }

    public Date getDate() {
        return _date;
    }

    public String getText() {
        return _text;
    }

}
