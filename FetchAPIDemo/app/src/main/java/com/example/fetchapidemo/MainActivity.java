package com.example.fetchapidemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private ListView listView;

    private RequestQueue requestQueue;
    private ListView listView;
    private List<String> textList;
    private List<String> fetchedItems; // List to store fetched items from API
    private Button loadNextButton;
    private int currentIndex = 0; // Index to track the next item to display

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        loadNextButton = findViewById(R.id.loadNextButton); // Initialize the button
        textList = new ArrayList<>();
        fetchedItems = new ArrayList<>(); // List to store API data

        // Initialize the RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // URL for the request
        String url = "https://cat-fact.herokuapp.com/facts";

        // Create a JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse the JSON array and store all items in fetchedItems list
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String text = jsonObject.getString("text");
                                fetchedItems.add(text); // Add each item to fetchedItems
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(MainActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);

        // Set an onClickListener for the button to load data one by one
        loadNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex < fetchedItems.size()) {
                    // Add the next item to the textList and update the ListView
                    textList.add(fetchedItems.get(currentIndex));
                    currentIndex++; // Move to the next item

                    // Update ListView with the new item
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, textList);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "No more items to load", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}