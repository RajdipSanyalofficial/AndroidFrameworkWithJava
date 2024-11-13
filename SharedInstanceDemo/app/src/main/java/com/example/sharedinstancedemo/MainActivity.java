package com.example.sharedinstancedemo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText name, designation;
    private RadioButton tnc;
    private Button saveBtn;
    String s, s1;
    boolean b;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.nametxt);
        designation = findViewById(R.id.desigtxt);
        tnc = findViewById(R.id.maler);
        saveBtn = findViewById(R.id.savebtn);
        name.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                s = String.valueOf(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {}    });
        designation.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s1 = String.valueOf(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {}    });
        tnc.setOnCheckedChangeListener((e, e1) -> {
            b = e1;
        });
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putString("name", s);
        outState.putString("designation", s1);
        outState.putBoolean("tnc", b);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstnceState)
    {
        s = savedInstnceState.getString("name");
        s1 = savedInstnceState.getString("designation");
        b = savedInstnceState.getBoolean("tnc");
        Toast.makeText(getApplicationContext(), "Saved Instance", Toast.LENGTH_SHORT).show();
        super.onRestoreInstanceState(savedInstnceState);
    }
}