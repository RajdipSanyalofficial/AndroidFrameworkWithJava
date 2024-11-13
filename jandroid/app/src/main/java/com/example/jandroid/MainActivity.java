package com.example.jandroid;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.jandroid.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Implicit

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/aryan1403"));

        //Explicit

        Intent intent1=new Intent(getApplicationContext(),SecondActivity.class);

        setContentView(R.layout.activity_main);
        startActivity(intent1);


        // Pending

        Intent pIntent = new Intent(this, SecondActivity.class);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, pIntent, PendingIntent.FLAG_UPDATE_CURRENT);
// notification (Notification Builder)

        setContentView(R.layout.activity_main);
        getApplicationContext().sendBroadcast(intent);
        getApplicationContext().sendOrderedBroadcast(intent, ""); // ordered
        startActivity(intent1);    // sticky
    }
}