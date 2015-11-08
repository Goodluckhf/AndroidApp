package com.bubyakin.tweetssearch.storage;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bubyakin.tweetssearch.models.User;

import java.util.ArrayList;

public class StorageDataProvider {
    private static volatile StorageDataProvider _instance;
    private SQLiteDatabase _db;
    private DatabaseHelper _dbHelper;

    public static StorageDataProvider getInstance() {
        if(_instance == null) {
            synchronized (StorageDataProvider.class) {
                if (_instance == null) {
                    _instance = new StorageDataProvider();
                }
            }
        }
        return _instance;
    }

   /* public StorageDataProvider setContext(Context ctx) {
        this._ctx = ctx;
        return this;
    }*/

    public void open(Context ctx) {
        this._dbHelper = new DatabaseHelper(ctx);
        this._db = this._dbHelper.getWritableDatabase();
    }

    public void close() {
        if (this._dbHelper!=null) {
            this._dbHelper.close();
        }
    }

    public void addUsers(ArrayList<User> users) {
        for(int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            ContentValues cv = new ContentValues();
            cv.put("name", user.getName());
            cv.put("friends_count", user.getFriendsCount());
            this._db.insert("user", null, cv);
        }
    }

    /*public User getUserById(int id) {
        *//*this._db.query("user", ,)
        this._db.execSQL("SELECT *" +
                            "FROM user" +
                            "WHERE id =" + id);*//*
    }

    public boolean removeUser(int id) {

    }*/

}




/*
import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteDatabase.CursorFactory;
        import android.database.sqlite.SQLiteOpenHelper;

public class DB {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "mytab";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_TXT = "txt";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_IMG + " integer, " +
                    COLUMN_TXT + " text" +
                    ");";

    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    // добавить запись в DB_TABLE
    public void addRec(String txt, int img) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TXT, txt);
        cv.put(COLUMN_IMG, img);
        mDB.insert(DB_TABLE, null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();
            for (int i = 1; i < 5; i++) {
                cv.put(COLUMN_TXT, "sometext " + i);
                cv.put(COLUMN_IMG, R.drawable.ic_launcher);
                db.insert(DB_TABLE, null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}*/
