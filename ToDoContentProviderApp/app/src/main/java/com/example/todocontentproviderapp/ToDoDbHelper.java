package com.example.todocontentproviderapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    public ToDoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TODO_TABLE = "CREATE TABLE " + ToDoContract.ToDoEntry.TABLE_NAME + " (" +
                ToDoContract.ToDoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ToDoContract.ToDoEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                ToDoContract.ToDoEntry.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                ToDoContract.ToDoEntry.COLUMN_NAME_COMPLETED + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ToDoContract.ToDoEntry.TABLE_NAME);
        onCreate(db);
    }
}

