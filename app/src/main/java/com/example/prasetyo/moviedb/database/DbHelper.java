package com.example.prasetyo.moviedb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context, DatabaseConstruct.DB_NAME, null, DatabaseConstruct.DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(doCreateDatabase());
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropDatabase());
        onCreate(db);
    }

    private String doCreateDatabase() {
        return DatabaseConstruct.createTable();
    }

    private String dropDatabase() {
        return DatabaseConstruct.dropTable();
    }
}
