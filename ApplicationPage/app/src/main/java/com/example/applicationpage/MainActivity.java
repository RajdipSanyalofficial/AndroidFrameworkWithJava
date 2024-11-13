package com.example.applicationpage;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;





public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout refresh;
    Button dialog,inputdialog,showdialog,dialogfrag;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_main);
        refresh=findViewById(R.id.swipeRefreshLayout);
        refresh.setOnRefreshListener(()->{
            refresh.setRefreshing(false);
        });
        dialog=findViewById(R.id.btnShowAlertDialog);
        dialog.setOnClickListener(view -> showAlertDialog());
        inputdialog=findViewById(R.id.btnShowInputDialog);
        inputdialog.setOnClickListener(view -> showInputDialog());
        dialogfrag=findViewById(R.id.btnShowDialogFragment);
        dialogfrag.setOnClickListener(view -> {
            DialogFragment newFragment = new CustomDialogFragment();
            newFragment.show(getSupportFragmentManager(), "dialog");
        });


        showdialog= findViewById(R.id.btnShowActivityDialog);
        showdialog.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DialogActivity.class);
            startActivity(intent);
        });

    }
    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Alert Dialog")
                .setMessage("This is a basic alert dialog.")
                .setPositiveButton("OK", (dialog, which) -> {})
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void showInputDialog() {
        CustomInputDialogFragment inputDialog = new CustomInputDialogFragment();
        inputDialog.show(getSupportFragmentManager(), "inputDialog");
    }
}