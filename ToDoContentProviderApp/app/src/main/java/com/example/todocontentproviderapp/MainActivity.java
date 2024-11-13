package com.example.todocontentproviderapp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter mCursorAdapter;
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;

    private static final int TODO_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleEditText = findViewById(R.id.edit_text_title);
        mDescriptionEditText = findViewById(R.id.edit_text_description);
        Button addButton = findViewById(R.id.button_add);
        ListView todoListView = findViewById(R.id.list_view_todo);

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
        todoListView.setAdapter(mCursorAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewToDo();
            }
        });

        getLoaderManager().initLoader(TODO_LOADER_ID, null, this);
    }

    private void insertNewToDo() {
        String title = mTitleEditText.getText().toString();
        String description = mDescriptionEditText.getText().toString();

        ContentValues values = new ContentValues();
        values.put(ToDoContract.ToDoEntry.COLUMN_NAME_TITLE, title);
        values.put(ToDoContract.ToDoEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(ToDoContract.ToDoEntry.COLUMN_NAME_COMPLETED, 0);

        getContentResolver().insert(ToDoContract.ToDoEntry.CONTENT_URI, values);
        mTitleEditText.setText("");
        mDescriptionEditText.setText("");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                ToDoContract.ToDoEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

}
