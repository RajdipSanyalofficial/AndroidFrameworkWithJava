package com.example.demom2;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
public class bgService extends IntentService {
    private static final String TAG = bgService.class.getSimpleName();
    public bgService() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {        // code
    }

    public void work()
    {
        Log.i(TAG, "hello world");
    }

}