package com.example.contentproviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

public class BookContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.contentprovider";
    private static final String TABLE_NAME = "tbl_book";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    private static final int BOOKS = 1;
    private static final int BOOK_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, BOOKS);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", BOOK_ID);
    }

    private DbManager dbManager;

    @Override
    public boolean onCreate() {
        dbManager = new DbManager(getContext());
        return (dbManager != null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != BOOKS) {
            throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        long rowID = dbManager.getWritableDatabase().insert(TABLE_NAME, null, values);
        if (rowID > 0) {
            Uri resultUri = Uri.withAppendedPath(CONTENT_URI, String.valueOf(rowID));
            getContext().getContentResolver().notifyChange(resultUri, null);
            return resultUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}

