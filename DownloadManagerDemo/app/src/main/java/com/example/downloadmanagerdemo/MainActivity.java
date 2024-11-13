package com.example.downloadmanagerdemo;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button download_btn;
    private ProgressBar pBar;
    private long downloadId;
    private DownloadManager downloadManager;
    private boolean isFinishedDownload = false;

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadId == id) {
                checkDownloadStatus();
            }
        }
    };

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        download_btn = findViewById(R.id.download_btn);
        pBar = findViewById(R.id.progressBar);

        download_btn.setOnClickListener(e -> download());

        // Initialize the DownloadManager
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }

    private void download() {
        String url = "https://thumbs.dreamstime.com/z/illustration-lord-ganesha-ganesh-chaturthi-ai-generated-illustration-lord-ganesha-ganesh-chaturthi-281152754.jpg";
        String filename = "lord_ganesha_image.jpg";

        // Define the destination file in the app's external files directory
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename);

        // Create a DownloadManager.Request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(file))
                .setTitle("Downloading Image")
                .setDescription("Downloading Lord Ganesha Image...")
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);

        // Enqueue the download request
        downloadId = downloadManager.enqueue(request);
    }

    private void checkDownloadStatus() {
        new Thread(() -> {
            while (!isFinishedDownload) {
                Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(downloadId));
                if (cursor.moveToFirst()) {
                    @SuppressLint("Range") int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (status) {
                        case DownloadManager.STATUS_FAILED:
                            isFinishedDownload = true;
                            break;
                        case DownloadManager.STATUS_PENDING:
                            // Handle pending status if needed
                            break;
                        case DownloadManager.STATUS_PAUSED:
                            // Handle paused status if needed
                            break;
                        case DownloadManager.STATUS_RUNNING:
                            @SuppressLint("Range") final long total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                            if (total >= 0) {
                                @SuppressLint("Range") final long downloaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                                int progress = (int) ((downloaded * 100L) / total);
                                runOnUiThread(() -> pBar.setProgress(progress));
                            }
                            break;
                        case DownloadManager.STATUS_SUCCESSFUL:
                            runOnUiThread(() -> {
                                pBar.setProgress(100);
                                Toast.makeText(this, "Downloaded successfully", Toast.LENGTH_SHORT).show();
                            });
                            isFinishedDownload = true;
                            break;
                    }
                }
                cursor.close();
                try {
                    Thread.sleep(500); // Sleep for a short duration before the next check
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}