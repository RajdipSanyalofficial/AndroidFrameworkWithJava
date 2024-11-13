package com.example.xmlparsing;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        private ListView lv;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ArrayList<user> userList = new ArrayList<>();
            user u = null;
            lv = findViewById(R.id.userList);
            try {
                InputStream is = getAssets().open("userdetails.xml");
                XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(is, null);
                String tag = "";
                String text = "";
                int event = xmlPullParser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT)
                {
                    tag = xmlPullParser.getName();
                    switch (event)
                    {
                        case XmlPullParser.START_TAG:
                            if(tag.equalsIgnoreCase("user"))
                            {
                                u = new user();
                            }
                            break;
                        case XmlPullParser.TEXT:
                            text = xmlPullParser.getText();
                            break;
                        case XmlPullParser.END_TAG:
                            if(tag.equalsIgnoreCase("user"))
                            {
                                userList.add(u);
                            }
                            else if(tag.equalsIgnoreCase("name"))
                            {
                                u.setName(text);
                            }
                            else if (tag.equalsIgnoreCase("designation"))
                            {
                                u.setDesignation(text);
                            }
                            break;
                        default:
                            break;
                    }
                    event = xmlPullParser.next();
                }
            } catch (IOException | XmlPullParserException e)
            {
                throw new RuntimeException(e);
            }
            ArrayAdapter<user> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,userList);
            lv.setAdapter(adapter);
        }
    }


