package com.csc.lesson4.jv.rss.reader;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Uri ITEM_CONTENT_URI = Uri
            .parse("content://com.csc.lesson4.jv.rss.reader.provider1/items");


    private Button btnGo;
    private EditText editText;
    private ListView listViewNews;

    private String inputUri;
    private ArrayList<RSSitem> rssItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        btnGo = (Button) findViewById(R.id.button);
        btnGo.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);
        inputUri = getIntent().getStringExtra(MainActivity.INPUT_URI);
        editText.setText(inputUri);

        listViewNews = (ListView) findViewById(R.id.listView);

        new ItemsDownload().execute();
    }


    @Override
    public void onClick(View v) {

        if (editText.getText().toString().trim().length() == 0) {
            Toast.makeText(this, R.string.no_url, Toast.LENGTH_LONG).show();
            return;
        }

        inputUri = editText.getText().toString();
        new ItemsDownload().execute();

    }


    class ItemsDownload extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            Uri uri = ITEM_CONTENT_URI.buildUpon()
                    .appendQueryParameter(MainActivity.INPUT_URI, inputUri)
                    .build();

            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(uri, null, null, null, null);

                rssItems = new ArrayList<>();
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        RSSitem item = new RSSitem();
                        item.setTitle(cursor.getString(cursor.getColumnIndex(RSSParser.TITLE)));
                        item.setDescription(cursor.getString(cursor.getColumnIndex(RSSParser.DESCRIPTION)));
                        item.setPubdate(cursor.getString(cursor.getColumnIndex(RSSParser.PUB_DATE)));

                        rssItems.add(item);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (rssItems == null || rssItems.isEmpty()) {
                Toast.makeText(NewsListActivity.this, R.string.no_news, Toast.LENGTH_LONG).show();
                return;
            }

            ListNewsAdapter adapter = new ListNewsAdapter(NewsListActivity.this, rssItems);
            listViewNews.setAdapter(adapter);
        }
    }
}
