package com.csc.telezhnaya.rss;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RSSActivity extends AppCompatActivity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        final String address = getIntent().getStringExtra(MainActivity.URL);

        new AsyncTask<String, Void, ArrayList<String>>() {
            @Override
            protected void onPostExecute(ArrayList<String> result) {
                super.onPostExecute(result);
                ListView rssListView = (ListView) findViewById(R.id.rss_list);
                rssListView.setAdapter(new ArrayAdapter<>(context, R.layout.list_item, result));
            }

            @Override
            protected ArrayList<String> doInBackground(String... params) {
                Cursor cursor = null;
                ArrayList<String> news = new ArrayList<>();
                String[] headers = {"pubDate", "title", "description"};
                try {
                    cursor = getContentResolver().query(RSSContentProvider.URI, headers, address, null, null, null);
                    while (cursor.moveToNext()) {
                        StringBuilder topic = new StringBuilder();
                        for (String header : headers) {
                            topic.append(cursor.getString(cursor.getColumnIndex(header))
                                    .replace("&quot;", "\"")).append("\n");
                        }
                        news.add(topic.toString());
                    }

                } finally {
                    cursor.close();
                }
                return news;
            }
        }.execute(address);
    }
}