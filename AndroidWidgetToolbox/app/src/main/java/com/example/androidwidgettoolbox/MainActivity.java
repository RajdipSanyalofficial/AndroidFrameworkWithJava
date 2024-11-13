package com.example.androidwidgettoolbox;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        CircularProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setProgress(50);
        Button compass = findViewById(R.id.compassView);
        compass.setOnClickListener(view -> {
            compassIntent(compass);
        });
    }
    public void submitName(View view) {
        EditText nameInput = findViewById(R.id.nameInput);
        String name = nameInput.getText().toString();
        Toast.makeText(this, "Hello " + name, Toast.LENGTH_SHORT).show();
    }
    private void compassIntent(View view){
        startActivity(new Intent(this,CompassActivity.class));
    }

}