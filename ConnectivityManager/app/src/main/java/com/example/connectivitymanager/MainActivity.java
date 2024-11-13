package com.example.connectivitymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView connectionStatusTextView;
    private TextView wifiInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionStatusTextView = findViewById(R.id.connection_status_text);
        wifiInfoTextView = findViewById(R.id.wifi_info_text);

        Button checkConnectivityButton = findViewById(R.id.check_connectivity_button);
        checkConnectivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetworkConnectivity();
            }
        });

        Button manageWiFiButton = findViewById(R.id.manage_wifi_button);
        manageWiFiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageWiFi();
            }
        });
    }

    // Method to check network connectivity status
    private void checkNetworkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // For Android 10+ use getNetworkCapabilities
        Network activeNetwork = connectivityManager.getActiveNetwork();
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);

        if (networkCapabilities != null) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                connectionStatusTextView.setText("Connected to Wi-Fi");
                getWiFiConnectionDetails();
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                connectionStatusTextView.setText("Connected to Mobile Data");
            } else {
                connectionStatusTextView.setText("Connected to some other network");
            }
        } else {
            connectionStatusTextView.setText("No network connection");
        }
    }

    // Method to retrieve and display Wi-Fi connection details
    private void getWiFiConnectionDetails() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        if (wifiInfo != null) {
            String ssid = wifiInfo.getSSID();
            int linkSpeed = wifiInfo.getLinkSpeed(); // Measured in Mbps
            int signalStrength = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 100); // Signal strength percentage

            String wifiDetails = "Connected to SSID: " + ssid + "\n" +
                    "Link Speed: " + linkSpeed + " Mbps\n" +
                    "Signal Strength: " + signalStrength + "%";

            wifiInfoTextView.setText(wifiDetails);
        } else {
            wifiInfoTextView.setText("No active Wi-Fi connection");
        }
    }

    // Method to enable/disable Wi-Fi and open Wi-Fi settings
    private void manageWiFi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
            Toast.makeText(this, "Wi-Fi Disabled", Toast.LENGTH_SHORT).show();
        } else {
            wifiManager.setWifiEnabled(true);
            Toast.makeText(this, "Wi-Fi Enabled", Toast.LENGTH_SHORT).show();

            // Open Wi-Fi settings
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);
        }
    }
}
