package com.example.guisynchronization;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static final int REQUEST_CODE = 101;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setAlarmButton = findViewById(R.id.set_alarm);
        Button cancelAlarmButton = findViewById(R.id.cancel_alarm);
        TextView selectedTimeTextView = findViewById(R.id.selected_time);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

// Initialize the calendar to set time
        calendar = Calendar.getInstance();

// Set the alarm on button click
        setAlarmButton.setOnClickListener(v -> showTimePickerDialog(selectedTimeTextView));

// Cancel the alarm
        cancelAlarmButton.setOnClickListener(v -> cancelAlarm());
    }

    private void showTimePickerDialog(TextView selectedTimeTextView) {
// Create a TimePickerDialog to allow the user to pick the time
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                (view, hourOfDay, minute) -> {
// Set the chosen time in the Calendar object
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

// Show the selected time
                    String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    selectedTimeTextView.setText("Alarm set for: " + selectedTime);

// Set the alarm
                    setAlarm();
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void setAlarm() {
// Intent to trigger the alarm
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

// Set the alarm for the selected time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    private void cancelAlarm() {
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }
    }
}