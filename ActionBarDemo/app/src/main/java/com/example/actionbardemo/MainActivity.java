package com.example.actionbardemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner navigationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set Toolbar properties
        getSupportActionBar().setTitle("My Custom Toolbar");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitle("Subtitle");
        toolbar.setSubtitleTextColor(Color.LTGRAY);
        toolbar.setNavigationIcon(R.drawable.backbtn); // Set custom navigation icon

        // Handling Up Navigation (Back button)
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Setup Spinner for dropdown (optional for navigation)
        navigationSpinner = new Spinner(this);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{"Home", "Settings", "Profile","Tab1","Tab2"});
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        navigationSpinner.setAdapter(spinnerAdapter);

        toolbar.addView(navigationSpinner); // Add Spinner to Toolbar

        // Load default fragment
        loadFragment(new HomeFragment());

        // Handling Spinner item selection
        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        loadFragment(new HomeFragment());
                        break;
                    case 1:
                        loadFragment(new SettingsFragment());
                        break;
                    case 2:
                        loadFragment(new ProfileFragment());
                        break;
                    case 3:
                        loadFragment(new TabFragment1());
                        break;
                    case 4:
                        loadFragment(new TabFragment2());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    // Method to load different fragments
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    // Inflate Toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handle Toolbar menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle search action
        if (item.getItemId() == R.id.action_search) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        }

        // Handle settings action
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}