package com.example.enhancingyourviews;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private CustomSurfaceView customSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize custom surface view for canvas drawing
        customSurfaceView = new CustomSurfaceView(this);
        setContentView(customSurfaceView);

        // Handle touch input on the surface view
        customSurfaceView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Toast.makeText(MainActivity.this, "Touch event detected", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }


    // Handle hardware button and D-Pad events
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            Toast.makeText(this, "D-Pad Center Pressed", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    // Handle trackball movements
    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        Toast.makeText(this, "Trackball event detected", Toast.LENGTH_SHORT).show();
        return true;
    }
}
