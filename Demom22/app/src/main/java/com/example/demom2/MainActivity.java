package com.example.demom2;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.util.Linkify;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.demom2.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.LongSummaryStatistics;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private ListView mylistView;

    private TextView txtview;

    private EditText txtview2;

    private Button addTodobtn;

    //private myBroadcastReceiver receiver = new myBroadcastReceiver();


/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();

    }


}
*/


    //private ListView mylistView;

    String tutorials[] = { /*"Algorithms", "Data Structures",
            "Languages", "Interview Corner",
            "GATE", "ISRO CS",
            "UGC NET CS", "CS Subjects",
            "Web Technologies"*/};

    //private TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        SharedPreferences sp =getSharedPreferences("mytodo",MODE_PRIVATE);
        SharedPreferences.Editor myEdit =sp.edit();

        //setContentView(R.layout.activity_main);
        //mylistView = findViewById(R.id.mylist);
        //ArrayAdapter<String> arr = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tutorials);



        //txtview = findViewById(R.id.mytextview);
        //String url = "https://github.com/aryan1403";
        //txtview.setText(url);
        //Pattern pattern = Pattern.compile("Android");
        //Linkify.addLinks(txtview, pattern, url);
        //mylistView.setAdapter(arr);
/*
        txtview = findViewById(R.id.mytextview1);
        String url1 = "https://facebook.com";
        txtview.setText(url1);
        Pattern pattern1 = Pattern.compile("Android");
        Linkify.addLinks(txtview, pattern1, url);
        mylistView.setAdapter(arr);

        txtview = findViewById(R.id.mytextview2);
        String url2 = "https://snapchat.com";
        txtview.setText(url2);
        Pattern pattern2= Pattern.compile("Android");
        Linkify.addLinks(txtview, pattern2, url);
        mylistView.setAdapter(arr);


        txtview = findViewById(R.id.mytextview3);
        String url3 = "https://instagram.com";
        txtview.setText(url3);
        Pattern pattern3 = Pattern.compile("Android");
        Linkify.addLinks(txtview, pattern3, url);
        mylistView.setAdapter(arr);


    */
/*
 DBHandler db = new DBHandler(this);
Log.d("Insert: ", "Inserting...");
db.addTodo("Do the homework");
db.addTodo("Do the homework2");
db.addTodo("Do the homework3");
Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();

 */

    txtview2 = findViewById(R.id.txt2);
    addTodobtn = findViewById(R.id.addtodo);
addTodobtn.setOnClickListener(e -> {
        String todo = txtview2.getText().toString();
        myEdit.putString("name", todo);
        Log.i("meow", "Inserted data");
        myEdit.commit();
        Toast.makeText(this,getData(),Toast.LENGTH_SHORT).show();
        doWork();
});





/*

myBroadcastReceiver receiver = new myBroadcastReceiver();
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);

 */

    }

    public void doWork()
    {
        Thread th =new Thread(() -> {
            runOnUiThread(() -> {
                txtview2.setText("");
            });
        });

        th.start();
    }

    public String getData()
    {
        SharedPreferences sp = getSharedPreferences("mytodo", MODE_PRIVATE);
        String todo = sp.getString("name", "");
        return todo;
    }

    public void task1()
    {
        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected void onPreExecute()
            {
// Creating a Sample Async Task
            }
            @Override
            protected String doInBackground(Void... voids)
            {
                return "";
            }
            @Override
            protected void onPostExecute(String result)
            {
            }

        }.execute();
    }
    private myBroadcastReceiver receiver = new myBroadcastReceiver();
    protected void onStart()
    {
        super.onStart();
        IntentFilter filter=new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(receiver,filter);
        bgService service=new bgService();

        try {
            service.onHandleIntent(Intent.getIntent(Intent.ACTION_ANSWER));
        }
        catch(Exception e)
        {

        }
    }


}
