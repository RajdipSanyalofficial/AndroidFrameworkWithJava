package com.example.screenorientation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    // declare button variable

    Button button;

    @Override

    protected void onCreate(Bundle savedInstanceState)

    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // initialise button with id

        button = findViewById(R.id.b1);

    }

    // onClickListener attached to button

    // to send intent to next activity

    public void onClick(View v)

    {

        // Create instance of intent and

        // startActivity with intent object

        Intent intent

                = new Intent(

                MainActivity.this,

                NextActivity.class);

        startActivity(intent);

    }

}

