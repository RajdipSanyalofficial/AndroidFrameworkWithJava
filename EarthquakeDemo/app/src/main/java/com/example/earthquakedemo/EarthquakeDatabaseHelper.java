package com.example.earthquakedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EarthquakeDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "earthquake.db";
    private static final int DATABASE_VERSION = 1;

    public EarthquakeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE earthquakes (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "magnitude REAL, " +
                "location TEXT, " +
                "date INTEGER, " +
                "details TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS earthquakes");
        onCreate(db);
    }
}
