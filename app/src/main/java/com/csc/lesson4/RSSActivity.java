package com.csc.lesson4;

import android.content.Intent;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RSSActivity extends AppCompatActivity {
    private String[] projection = new String[]{"Title", "Description", "Date"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        Intent intent = getIntent();
        String rssUrl = intent.getStringExtra(MainActivity.RSS_QUERY);
        if (!(rssUrl.startsWith("http://") || rssUrl.startsWith("https://"))) {
            rssUrl = "http://" + rssUrl;
        }
        new DownloadAndSaveRSS().execute(rssUrl, findViewById(R.id.listView));
    }


    public class DownloadAndSaveRSS extends AsyncTask<Object, Void, Channel> {
        private ListView listView;

        @Override
        protected Channel doInBackground(Object... objects) {
            String url = (String) objects[0];
            listView = (ListView) objects[1];
            Channel channel = null;
            try {
                channel = loadXmlFromNetwork(url);
            } catch (Exception e) {
                return null;
            }
            return channel;
        }

        @Override
        protected void onPostExecute(Channel channel) {
            if (channel == null) {
                Toast.makeText(RSSActivity.this, "Unable to load RSS feed, please try again", Toast.LENGTH_LONG).show();
                return;
            }

            super.onPostExecute(channel);
            MatrixCursor matrixCursor = new MatrixCursor(projection);
            try {
                for (Item item : channel.data) {
                    matrixCursor.addRow(new String[]{item.title, item.description, item.date});
                }
            } finally {
                matrixCursor.close();
            }
            new LoadAndDisplayRSS().execute(matrixCursor, listView);
        }

        private Channel loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
            InputStream stream = null;
            RSSParser parser = new RSSParser();
            Channel channel = null;

            try {
                stream = downloadUrl(urlString);
                channel = parser.parse(stream);
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
            return channel;
        }

        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            return conn.getInputStream();
        }
    }


    class LoadAndDisplayRSS extends AsyncTask<Object, Void, List<Item>> {
        private ListView listView;

        @Override
        protected List<Item> doInBackground(Object... objects) {
            MatrixCursor cursor = (MatrixCursor) objects[0];
            listView = (ListView) objects[1];
            ArrayList<Item> data = new ArrayList<>();
            try {
                while (cursor.moveToNext()) {
                    data.add(new Item(cursor.getString(cursor.getColumnIndex(projection[0])),
                                    cursor.getString(cursor.getColumnIndex(projection[1])),
                                    cursor.getString(cursor.getColumnIndex(projection[2])))
                    );
                }
            } finally {
                cursor.close();
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<Item> result) {
            super.onPostExecute(result);
            RSSListAdapter adapter = new RSSListAdapter(RSSActivity.this, result);
            listView.setAdapter(adapter);
        }
    }
}
