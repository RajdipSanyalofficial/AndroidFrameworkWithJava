package com.example.hardwaresensors;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer, magnetometer, gyroscope, barometer;
    private TextView textOrientation, textAcceleration, textPressure;
    private ImageView compass;

    private float[] gravity;
    private float[] geomagnetic;
    private float[] rotationMatrix = new float[9];
    private float[] orientationValues = new float[3];
    private float[] acceleration = new float[3];
    private float pressureValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textOrientation = findViewById(R.id.textOrientation);
        textAcceleration = findViewById(R.id.textAcceleration);
        textPressure = findViewById(R.id.textPressure);
        compass = findViewById(R.id.compass);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            acceleration = event.values.clone();
            updateAccelerationText();
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values.clone();
            updateOrientation();
        } else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            pressureValue = event.values[0];
            textPressure.setText(String.format("Pressure: %.2f hPa", pressureValue));
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // Handle gyroscope data if needed
        }
    }

    private void updateAccelerationText() {
        String accelerationText = String.format("Acceleration: X: %.2f, Y: %.2f, Z: %.2f",
                acceleration[0], acceleration[1], acceleration[2]);
        textAcceleration.setText(accelerationText);
    }

    private void updateOrientation() {
        if (gravity != null && geomagnetic != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            if (SensorManager.getRotationMatrix(R, I, acceleration, geomagnetic)) {
                SensorManager.getOrientation(R, orientationValues);
                double azimuth = Math.toDegrees(orientationValues[0]); // Rotation around Z-axis
                textOrientation.setText(String.format("Orientation: %.2f°", azimuth));

                // Rotate compass image
                compass.setRotation((float) -azimuth);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle sensor accuracy changes if necessary
    }
}
