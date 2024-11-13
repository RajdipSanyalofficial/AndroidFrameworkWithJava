package com.example.contentresolver;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    Uri uri = Uri.parse("content://com.example.mycontentprovider/data");
    List<String> names=new ArrayList<>();
    List<String> data=new ArrayList<>();
    Button getTotalData,getNames,getLoaderbtn;
    ListView namesList,dataList,loaderList;
    private SimpleCursorAdapter adapter;

    private static final int LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        getTotalData=findViewById(R.id.getData);
        getNames=findViewById(R.id.getnames);
        getLoaderbtn=findViewById(R.id.getLoader);
        loaderList=findViewById(R.id.loaderList);
        namesList=findViewById(R.id.namesList);
        dataList=findViewById(R.id.dataList);
        loaderList = findViewById(R.id.loaderList);

        getTotalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolver resolver = getContentResolver();
                Cursor cursor = resolver.query(uri, null, null, null, null);

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range") String value = cursor.getString(cursor.getColumnIndex("value"));
                        data.add(name+", "+value);
                        Log.d("ContentProvider", "Name: " + name + ", Value: " + value);
                    }
                    cursor.close();
                }
                ArrayAdapter<String> adapter= new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,data);
                dataList.setAdapter(adapter);
            }
        });



        getNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] projection = {"name"}; // Columns to retrieve

                Cursor cursor = getContentResolver().query(uri, projection, null, null, "name ASC");

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                        Log.d("QueryExample", "Name: " + name);
                        names.add(name);
                    }
                    cursor.close();
                }
                ArrayAdapter<String> namesAdapter= new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,names);
                namesList.setAdapter(namesAdapter);
            }
        });

        getLoaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namesList.setVisibility(View.GONE);
                dataList.setVisibility(View.GONE);
                loaderList.setVisibility(View.VISIBLE);
                getNames.setVisibility(View.GONE);
                getTotalData.setVisibility(View.GONE);




        //loader


        // Initialize the SimpleCursorAdapter
        String[] fromColumns = {"name", "value"};
        int[] toViews = {android.R.id.text1, android.R.id.text2}; // The TextViews in simple_list_item_2 layout

        adapter = new SimpleCursorAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_2, // Use built-in layout
                null, // No cursor yet
                fromColumns,
                toViews,
                0
        );

        // Set the adapter to the ListView
        loaderList.setAdapter(adapter);

        // Initialize the loader
        LoaderManager.getInstance(MainActivity.this).initLoader(LOADER_ID, null, MainActivity.this);
            }
        });
        
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define the Uri and projection for the query
        Uri uri = Uri.parse("content://com.example.mycontentprovider/data");
        String[] projection = {"_id", "name", "value"};
        return new CursorLoader(this, uri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor into the adapter
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Remove the cursor from the adapter
        adapter.swapCursor(null);
    }
}