package com.example.contentproviders;



import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;



import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbManager extends SQLiteOpenHelper {
    private static final String dbname="bookdesc.db";
    public DbManager(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry ="create table tbl_book (id integer primary key autoincrement, name text, cost int, pages int)";
        db.execSQL(qry);
        System.out.println("I AM HERE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_book");
        onCreate(db);
    }
    public String addRecord (String p1, int p2, int p3)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",p1);
        cv.put("cost",p2);
        cv.put("pages",p3);

        long res=db.insert("tbl_book", null,cv);

        if(res==-1)
            return "Failed";
        else
            return "Successfully Inserted";

    }
}

