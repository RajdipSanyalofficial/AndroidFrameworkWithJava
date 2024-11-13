package com.example.todocontentproviderapp;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    private SimpleCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ListView searchListView = findViewById(R.id.list_view_search);
        String[] fromColumns = {
                ToDoContract.ToDoEntry.COLUMN_NAME_TITLE,
                ToDoContract.ToDoEntry.COLUMN_NAME_DESCRIPTION
        };
        int[] toViews = {
                R.id.text_view_title,
                R.id.text_view_description
        };
        mCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.todo_list_item,
                null,
                fromColumns,
                toViews,
                0);
        searchListView.setAdapter(mCursorAdapter);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Uri uri = ToDoContract.ToDoEntry.CONTENT_URI.buildUpon().appendPath("search").build();
            Cursor cursor = getContentResolver().query(uri, null, ToDoContract.ToDoEntry.COLUMN_NAME_TITLE + " LIKE ?", new String[]{"%" + query + "%"}, null);
            mCursorAdapter.swapCursor(cursor);
        }
    }
}
