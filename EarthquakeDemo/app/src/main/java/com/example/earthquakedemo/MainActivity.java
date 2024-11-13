package com.example.earthquakedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView earthquakeListView;
    private ArrayList<String> earthquakeList;
    private ArrayAdapter<String> adapter;
    private TextView tvResult;
    private static final int PICK_CONTACT_REQUEST = 1;

    private static final String PROVIDER_URI = "content://com.example.earthquakedemo/earthquakes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tv_result);

        Button btnSearchEarthquakes = findViewById(R.id.btn_search_earthquakes);
        Button btnLoadImages = findViewById(R.id.btn_load_images);
        Button btnLoadContacts = findViewById(R.id.btn_load_contacts);
        Button btnLoadCalendarEvents = findViewById(R.id.btn_load_calendar_events);
        Button btnPickContact = findViewById(R.id.btn_pick_contact);
        earthquakeListView = findViewById(R.id.earthquakeListView);
        earthquakeList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, earthquakeList);
        earthquakeListView.setAdapter(adapter);
        // Request permissions for accessing external content providers
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        // Search for earthquakes (this is a placeholder; you can integrate an earthquake provider or API)
        btnSearchEarthquakes.setOnClickListener(v -> searchEarthquakes(5.0f));

        // Load images from MediaStore
        btnLoadImages.setOnClickListener(v -> loadImagesFromMediaStore());

        // Load contacts from ContactsProvider
        btnLoadContacts.setOnClickListener(v -> loadContacts());

        // Load calendar events from CalendarContract
//        btnLoadCalendarEvents.setOnClickListener(v -> loadCalendarEvents());

        // Pick a contact from the phonebook
        btnPickContact.setOnClickListener(v -> pickContact());

        // Insert dummy earthquake data into the provider
        insertDummyData();

        // Query the earthquake data from the provider and display it
        loadEarthquakeData();
    }

    private void insertDummyData() {
        // Uri for accessing the content provider
        Uri uri = Uri.parse(PROVIDER_URI);

        // Insert a few earthquake records
        ContentValues values = new ContentValues();

        values.put("magnitude", 6.1);
        values.put("location", "California");
        values.put("date", System.currentTimeMillis());

        values.put("details", "Moderate earthquake in California");
        getContentResolver().insert(uri, values);

        values.put("magnitude", 7.4);
        values.put("location", "Japan");
        values.put("date", System.currentTimeMillis());
        values.put("details", "Strong earthquake in Japan");
        getContentResolver().insert(uri, values);

        values.put("magnitude", 5.2);
        values.put("location", "Indonesia");
        values.put("date", System.currentTimeMillis());
        values.put("details", "Minor earthquake in Indonesia");
        getContentResolver().insert(uri, values);
    }

    private void loadEarthquakeData() {
        // Query all earthquake records from the provider
        Uri uri = Uri.parse(PROVIDER_URI);
        Cursor cursor = getContentResolver().query(uri, null, null, null, "date DESC");

        // Clear the list before adding new data
        earthquakeList.clear();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Get data from cursor
                @SuppressLint("Range") float magnitude = cursor.getFloat(cursor.getColumnIndex("magnitude"));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex("location"));
                @SuppressLint("Range") long date = cursor.getLong(cursor.getColumnIndex("date"));
                @SuppressLint("Range") String details = cursor.getString(cursor.getColumnIndex("details"));
                String formatDate = formatDate(date);
                // Format the earthquake info
                String earthquakeInfo = "Magnitude: " + magnitude + "\nLocation: " + location + "\nDetails: " + details+ "\nDate: " + formatDate;
                earthquakeList.add(earthquakeInfo);
            }
            cursor.close();
        }

        // Notify adapter that data has changed
        adapter.notifyDataSetChanged();
    }
    private String formatDate(long dateInMillis) {
        // Create a date object from milliseconds
        Date date = new Date(dateInMillis);
        // Define the date format
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        // Return formatted date
        return sdf.format(date);
    }
    // 4. Search for Earthquakes (Placeholder for now)
    private void searchEarthquakes(float minMagnitude) {
        // This is a placeholder - implement your earthquake content provider here
        tvResult.setText("Searching earthquakes with magnitude > " + minMagnitude);
    }

    // 6. Load images from MediaStore
    private void loadImagesFromMediaStore() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        StringBuilder images = new StringBuilder("Images:\n");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                images.append(imagePath).append("\n");
            }
            cursor.close();
        }
        tvResult.setText(images.toString());
    }

    // 7. Load contacts from ContactsProvider
    private void loadContacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        StringBuilder contacts = new StringBuilder("Contacts:\n");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.append(displayName).append("\n");
            }
            cursor.close();
        }
        tvResult.setText(contacts.toString());
    }

    // 10. Pick a contact
    private void pickContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri contactUri = data.getData();
            if (contactUri != null) {
                String contactId = contactUri.getLastPathSegment();
                loadContactDetails(contactId);
            }
        }
    }

    // 9. Load details for the selected contact
    private void loadContactDetails(String contactId) {
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{contactId}, null);

        StringBuilder contactDetails = new StringBuilder("Contact Details:\n");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactDetails.append("Phone: ").append(phoneNumber).append("\n");
            }
            cursor.close();
        }
        tvResult.setText(contactDetails.toString());
    }
}
