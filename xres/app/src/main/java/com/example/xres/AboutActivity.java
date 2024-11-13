package com.example.xres;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.about_activity);
        Intent data = new Intent();
// String text = "result from aaryan";
// data.setData(Uri.parse(text));
        data.putExtra("name", "Rajdip Sanyal");
        data.putExtra("role", "Developer");
        data.putExtra("exp", 1);
        setResult(RESULT_OK, data);
        finish();
    }
}