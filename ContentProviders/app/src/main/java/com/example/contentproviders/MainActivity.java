package com.example.contentproviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextCost, editTextPages;
    private Button buttonInsert;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextCost = findViewById(R.id.editTextCost);
        editTextPages = findViewById(R.id.editTextPages);
        buttonInsert = findViewById(R.id.buttonInsert);
        textViewResult = findViewById(R.id.textViewResult);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRecord();
            }
        });
    }

    private void insertRecord() {
        String name = editTextName.getText().toString();
        int cost = Integer.parseInt(editTextCost.getText().toString());
        int pages = Integer.parseInt(editTextPages.getText().toString());

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("cost", cost);
        values.put("pages", pages);

        Uri uri = getContentResolver().insert(Uri.parse("content://com.example.contentprovider/tbl_book"), values);
        if (uri != null) {
            textViewResult.setText("Record Inserted: " + uri.toString());
        } else {
            textViewResult.setText("Failed to Insert Record");
        }
    }
}
