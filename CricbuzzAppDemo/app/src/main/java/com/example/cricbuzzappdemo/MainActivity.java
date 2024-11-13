package com.example.cricbuzzappdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private String apiUrl = "https://api.cricapi.com/v1/countries?apikey=41452c46-0782-42d1-a21e-75e088539a24&offset=0";
    private RequestQueue rq;
    private CountryAdapter countryAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        getData();
    }
    private void getData() {
        rq = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.i("data", obj.toString());
                try {
                    JSONObject data = (JSONObject) obj.getJSONArray("data").get(0);
                    JSONArray countriesArray = obj.getJSONArray("data");
                    System.out.println("response=======>"+countriesArray);
                    String id = data.getString("id");
                    String name = data.getString("name");
                    String genericFlag = data.getString("genericFlag");
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    CountryAdapter adapter = new CountryAdapter(countriesArray, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(MainActivity.this, id + ", " + name + ", " + genericFlag, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, err -> {
            Toast.makeText(MainActivity.this, err.toString(), Toast.LENGTH_SHORT).show();
        });
        rq.add(jor);
    }

}

