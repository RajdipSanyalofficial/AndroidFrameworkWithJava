package com.example.guisynchronization;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class AlarmIntentService extends IntentService {
    public AlarmIntentService() {
        super("AlarmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
// Perform network refresh or background tasks
        performNetworkRefresh();
    }

    private void performNetworkRefresh() {
// Simulate network refresh
        Log.d("AlarmIntentService", "Refreshing network data...");
// Add network request logic here
    }
}
