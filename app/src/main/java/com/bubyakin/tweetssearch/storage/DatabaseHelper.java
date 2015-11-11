package com.bubyakin.tweetssearch.storage;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Twitter_app_cache";
    private static final Integer DB_VERSION = 1;
    private static final String DB_CREATE_USER =
        "create table user(" +
            "id integer primary key, " +
            "name varchar, " +
            "friends_count  integer, " +
            "image_uri  string" +
        ");";

    private static final String DB_CREATE_TWEET =
        "create table tweet(" +
            "id integer primary key, " +
            "user_id integer REFERENCES user(id), " +
            "date varchar, " +
            "text text " +
        ");";
    private static final String DB_DROP_TWEET = "DROP TABLE IF EXISTS tweet;";

    private static final String DB_DROP_USER = "DROP TABLE IF EXISTS user;";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.creatTweetTable(db);
        this.creatUserTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void creatUserTable(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_USER);
    }

    public void creatTweetTable(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_TWEET);
    }

    public void dropUserTable(SQLiteDatabase db) {
        db.execSQL(DB_DROP_USER);
    }

    public void dropTweetTable(SQLiteDatabase db) {
        db.execSQL(DB_DROP_TWEET);
    }

}
