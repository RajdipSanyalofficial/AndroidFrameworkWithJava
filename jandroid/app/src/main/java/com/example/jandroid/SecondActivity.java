package com.example.jandroid;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SecondActivity extends AppCompatActivity {

    ListView lview;

    private RequestQueue rq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.second_activity);
        getData();

        lview = findViewById(R.id.mylist);
        String[] names = {"Rajdip", "Pritam", "Sourya", "Anil", "Bijoy"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.mylistviewblock, R.id.itemtv, names);
        lview.setAdapter(adapter);
    }

        public void getData () {
            String url = "https://cat-fact.herokuapp.com/facts";
            String url2 = "https://dog-api.kinduff.com/api/facts";
            rq = Volley.newRequestQueue(this);
            StringRequest sr = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Toast.makeText(SecondActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(SecondActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject obj) {
                    //Toast.makeText(SecondActivity.this, s, Toast.LENGTH_SHORT).show();
                    Log.i("data", obj.toString());
                    try {
                        String fact = (String) obj.getJSONArray("facts").get(0);
                        Toast.makeText(SecondActivity.this, fact, Toast.LENGTH_SHORT).show();
// names[0] = fact;
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(SecondActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            rq.add(jor);

        }
    }

