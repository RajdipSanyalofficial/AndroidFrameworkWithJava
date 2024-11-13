package com.example.mydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {
    private static final String dbname = "student_details3.db";

    public DBManager(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "create table student_details3(id integer primary key autoincrement,name text, email text,course text)";
        db.execSQL(qry);
        System.out.println("I am here");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS student_details3 ");
        onCreate(db);
    }

    public String addRecord(String p1, String p2, String p3) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", p1);
        cv.put("name", p2);
        cv.put("name", p3);

        long res = db.insert("student_details3", null, cv);

        if (res == -1)
            return "Failed";

        else
            return "Successfully Inserted";

    }
}

