package com.example.xres;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.content.Context.TELEPHONY_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
//import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {


    ImageView iv;

    TextView name;

    Button blink_anim, z_amin;

    Toolbar toolbar;

    NotificationManager notificationManager;

    NotificationChannel notificationChannel;

    private final String channelId = "i.apps.notificetions";

    private final String desc = "Test notificetion";

    private final int CAMERA_REQUEST = 12233;

    MediaPlayer mediaPlayer;

    private static final int REQUEST_BLUETOOTH_PERMISSION = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        iv = findViewById(R.id.iv);
        iv.setImageResource(R.drawable.xresimg2);
        blink_anim = findViewById(R.id.anim);
        z_amin = findViewById(R.id.zanim);

        blink_anim.setOnClickListener(e -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
            iv.startAnimation(animation);

        });

        z_amin.setOnClickListener(e -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_animation);
            //iv.startAnimation(animation);
            //startActivityForResult(new Intent(this, AboutActivity.class), 1);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to continue ?");
            builder.setTitle("Get Result from About");
            builder.setCancelable(true);
            //playAudio();
            //checkBluetoothPermission();
            //turnWifiOn();
            builder.setPositiveButton("Yes",
                    (DialogInterface.OnClickListener) (dialog, which) -> {
                        startActivityForResult(new Intent(this, AboutActivity.class), 1);
// finish();
                    });
            builder.setNegativeButton("No",
                    (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });
            AlertDialog dialog = builder.create();
            //dialog.show();


            // notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Intent intent = new Intent(this, AboutActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Toast.makeText(this, "Yay!!!", Toast.LENGTH_SHORT).show();
                notificationChannel = new NotificationChannel(channelId, desc, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(false);
                notificationManager.createNotificationChannel(notificationChannel);
                Notification.Builder builder1 = new Notification.Builder(this, channelId)
                        .setSmallIcon(R.drawable.xresimg2)
                        .setContentIntent(pendingIntent);
                notificationManager.notify(1234, builder1.build());

            } else {


            }
            //Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //startActivityForResult(cameraIntent, CAMERA_REQUEST);

            //checkBluetoothPermission();

            //turnWifiOn();

            playAudio();
            checkBluetoothPermission();

        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);


        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("My App");
        //actionBar.setSubtitle("spiderman app");
        //actionBar.setIcon(R.drawable.xresimg2);
        //actionBar.setDisplayUseLogoEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

    }

     private void turnWifiOn() {
         WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
         if (wifi.isWifiEnabled()) {
             Toast.makeText(this, "Wifi is already on", Toast.LENGTH_SHORT).show();
         } else {
             wifi.setWifiEnabled(true);
             Toast.makeText(this, "Turned on Wifi", Toast.LENGTH_SHORT).show();

         }


    }




    private void checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12 and above
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                // Request BLUETOOTH_CONNECT permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.BLUETOOTH_CONNECT},
                        REQUEST_BLUETOOTH_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with Bluetooth operation
                Toast.makeText(this, "Bluetooth permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "Bluetooth permission denied", Toast.LENGTH_SHORT).show();
            }

        }
    }



     private void playAudio() {
        String audioUrl = "https://file-examples.com/storage/feabe3b8ad66e50249523a7/2017/11/file_example_MP3_2MG.mp3";

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {

            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Audio Started Playing....", Toast.LENGTH_SHORT).show();

    }



   public void onButtonClick(View v) {

            VideoView videoview = (VideoView) findViewById(R.id.videoview);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test);
            videoview.setVideoURI(uri);
            videoview.start();
        }





/*
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1 && resultCode == RESULT_OK) {
        String name = data.getStringExtra("name");
        String role = data.getStringExtra("role");
        int exp = data.getIntExtra("exp", -1);
        Toast.makeText(getApplicationContext(), name + ", " + role + ", " + exp, Toast.LENGTH_SHORT).show();
        Log.i("data", name + ", " + role + ", " + exp);
    } else if (requestCode == CAMERA_REQUEST) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        iv.setImageBitmap(photo);
    }


    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1 && resultCode == RESULT_OK)
    {
        String name = data.getStringExtra("name");
        String role = data.getStringExtra("role");
        int exp = data.getIntExtra("exp", -1);
        Toast.makeText(getApplicationContext(), name + ", " + role + ", " + exp, Toast.LENGTH_SHORT).show();
        Log.i("data", name + ", " + role + ", " + exp);
    }

}

@Override
public void onConfigurationChanged(Configuration configuration)
{
    super.onConfigurationChanged(configuration);
    if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    {
        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
    }
    else if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
    {
        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
    }
}

@Override
public boolean onCreateOptionsMenu(Menu menu)
{
    getMenuInflater().inflate(R.menu.menu_main,menu);
    return true;
}
@Override
public boolean onOptionsItemSelected(MenuItem item)
{
    int id = item.getItemId();
    if(id==R.id.item1){
        //handle action for item1
        return true;
    } else if (id==R.id.item2) {
        //handle action for item2
        return true;
    } else if (id==R.id.item3) {
        //handle action for item3
        return true;
    }

    return super.onOptionsItemSelected(item);

}*/


}



