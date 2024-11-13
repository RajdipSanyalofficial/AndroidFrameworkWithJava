package com.example.todocontentproviderapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class ToDoProvider extends ContentProvider {

    private static final int TODOS = 100;
    private static final int TODO_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ToDoDbHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ToDoContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, ToDoContract.PATH_TODO, TODOS);
        matcher.addURI(authority, ToDoContract.PATH_TODO + "/#", TODO_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new ToDoDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case TODOS:
                retCursor = db.query(ToDoContract.ToDoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TODO_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = ToDoContract.ToDoEntry._ID + "=?";
                String[] mSelectionArgs = new String[]{id};
                retCursor = db.query(ToDoContract.ToDoEntry.TABLE_NAME, projection, mSelection, mSelectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long id;
        switch (sUriMatcher.match(uri)) {
            case TODOS:
                id = db.insert(ToDoContract.ToDoEntry.TABLE_NAME, null, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (id > 0) {
            Uri returnUri = ContentUris.withAppendedId(ToDoContract.ToDoEntry.CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(returnUri, null);
            return returnUri;
        }
        throw new android.database.SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;
        switch (sUriMatcher.match(uri)) {
            case TODO_ID:
                String id = uri.getPathSegments().get(1);
                rowsDeleted = db.delete(ToDoContract.ToDoEntry.TABLE_NAME, ToDoContract.ToDoEntry._ID + "=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated;
        switch (sUriMatcher.match(uri)) {
            case TODO_ID:
                String id = uri.getPathSegments().get(1);
                rowsUpdated = db.update(ToDoContract.ToDoEntry.TABLE_NAME, values, ToDoContract.ToDoEntry._ID + "=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case TODOS:
                return ToDoContract.ToDoEntry.CONTENT_LIST_TYPE;
            case TODO_ID:
                return ToDoContract.ToDoEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }
}

