package com.csc.lesson4;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RSSReaderActivity extends AppCompatActivity {

    public String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssreader);
        Intent intent = getIntent();
        address = intent.getStringExtra(MainActivity.ADDRESS);
        Cursor cursor = null;
        String[] projection = {"pubDate", "title", "description"};
        ArrayList<String> rssItems = new ArrayList();
        try {
            cursor = getContentResolver().query(RSSContentProvider.URI, projection, address, null, null, null);
            while (cursor.moveToNext()) {
                StringBuilder news = new StringBuilder();
                for (String a: projection) {
                    int i = cursor.getColumnIndex(a);
                    if (a.equals("pubDate")) {
                        String date = cursor.getString(i);
                        date = date.replace("2016", "2016;");
                        news.append(date + "\n");
                    } else {
                        news.append(cursor.getString(i).replace("&quot;", "\"") + "\n");
                    }
                }
                rssItems.add(news.toString());

            }
        } finally {
            cursor.close();
        }
        ListView rssListView = (ListView) findViewById(R.id.rssListView);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, R.layout.list_item, rssItems);
        rssListView.setAdapter(aa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rssreader, menu);
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
}
