package com.bubyakin.tweetssearch.storage;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bubyakin.tweetssearch.R;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Twitter_app_cache";
    private static final Integer DB_VERSION = 1;
    private static final String DB_CREATE =
            "create table user(" +
                    "id integer primary key autoincrement, " +
                    "name varchar, " +
                    "friends_count  integer" +
                    ");" +
                    "create table tweet(" +
                    "id integer primary key autoincrement" +
                    "date date" +
                    "text text" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // создаем и заполняем БД
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
