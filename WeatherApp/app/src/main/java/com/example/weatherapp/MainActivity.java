package com.example.weatherapp;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etCityName;
    private Button btnSearch;
    private RecyclerView recyclerViewForecast;
    private ForecastAdapter forecastAdapter;
    private ArrayList<WeatherData> forecastList;

    private final String API_KEY = "904c6d12920ee7826474f212e2324691";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        etCityName = findViewById(R.id.etCityName);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerViewForecast = findViewById(R.id.recyclerViewForecast);

        // Set RecyclerView
        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(this));
        forecastList = new ArrayList<>();
        forecastAdapter = new ForecastAdapter(forecastList);
        recyclerViewForecast.setAdapter(forecastAdapter);

        // Button click listener to fetch weather
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etCityName.getText().toString().trim();
                if (!city.isEmpty()) {
                    new FetchWeatherTask().execute(city);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // AsyncTask to fetch weather data in background
    private class FetchWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String cityName = params[0];
            String urlStr = "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&appid=" + API_KEY + "&units=metric";
            try {
                URL url = new URL(urlStr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                parseJSON(result);
            } else {
                Toast.makeText(MainActivity.this, "Error fetching weather data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Parse JSON response from API
    private void parseJSON(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray listArray = jsonObject.getJSONArray("list");

            forecastList.clear();
            for (int i = 0; i < listArray.length(); i++) {
                JSONObject forecastObj = listArray.getJSONObject(i);

                String date = forecastObj.getString("dt_txt");
                JSONObject mainObj = forecastObj.getJSONObject("main");
                String temperature = mainObj.getString("temp");
                JSONArray weatherArray = forecastObj.getJSONArray("weather");
                JSONObject weatherObj = weatherArray.getJSONObject(0);
                String description = weatherObj.getString("description");
                String icon = weatherObj.getString("icon");

                // Add parsed data to the list
                forecastList.add(new WeatherData(date, temperature, description, icon));
            }

            // Notify adapter to update UI
            forecastAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}