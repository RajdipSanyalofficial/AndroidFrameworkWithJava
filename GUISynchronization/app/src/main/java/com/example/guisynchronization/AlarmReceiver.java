package com.example.guisynchronization;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
// Start the intent service to perform background tasks
        Intent serviceIntent = new Intent(context, AlarmIntentService.class);
        context.startService(serviceIntent);
    }
}
