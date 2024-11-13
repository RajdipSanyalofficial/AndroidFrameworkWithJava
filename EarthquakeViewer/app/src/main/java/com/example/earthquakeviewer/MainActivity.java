package com.example.earthquakeviewer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ListView to display earthquake data
        ListView earthquakeListView = findViewById(R.id.earthquakeList);

        // Sample earthquake data (can be replaced with actual data fetching)
        ArrayList<String> earthquakeData = new ArrayList<>();
        earthquakeData.add("6.1 - San Francisco");
        earthquakeData.add("4.8 - Los Angeles");
        earthquakeData.add("5.3 - New York");
        earthquakeData.add("7.0 - Tokyo");
        earthquakeData.add("3.5 - Berlin");


        // Adapter to display the list of earthquakes
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, earthquakeData);
        earthquakeListView.setAdapter(adapter);

        // Button to go to Settings Activity
        Button settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(view -> {
            // Launch Settings Activity
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
