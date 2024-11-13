package com.example.demom2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class myBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(isAirplaneModeActivated(context.getApplicationContext())) {
            Toast.makeText(context, "Airplane mode was on", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Airplane mode was off", Toast.LENGTH_SHORT).show();
        }
    }
    private static boolean isAirplaneModeActivated(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }
}
