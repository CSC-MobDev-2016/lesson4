package com.csc.smax.reader;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RSSActivity extends Activity implements TextView.OnEditorActionListener {

    private static final String DEFAULT_NEWS_URL = "https://news.yandex.ru/football.rss";
    private static final String[] RSS_TAGS = {"title", "context", "date"};

    private EditText inputText;
    private RecyclerView recyclerView;
    protected RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rss);

        inputText = (EditText) findViewById(R.id.input_text);
        String word = getIntent().getStringExtra(MainActivity.WORD);
        inputText.setText(word);
        inputText.setOnEditorActionListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RVAdapter();

        updatePage(word);
    }

    public void onClick(View view) {
        updatePage(inputText.getText().toString());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            updatePage(v.getText().toString());
            return true;
        }
        return false;
    }

    private void updatePage(String text) {

        new AsyncTask<String, Void, List<String>>() {

            @Override
            protected void onPostExecute(List<String> items) {
                super.onPostExecute(items);
                adapter.setRSS(items);
                recyclerView.setAdapter(adapter);
            }

            @Override
            protected List<String> doInBackground(String... params) {
                List<String> items = new ArrayList<>();
                try (Cursor cursor = getContentResolver().query(SmaxRSSReaderContentProvider.URI, RSS_TAGS, params[0], null, null, null)) {
                    while (cursor != null && cursor.moveToNext()) {
                        StringBuilder item = new StringBuilder();
                        for (String tag : RSS_TAGS) {
                            item.append(cursor.getString(cursor.getColumnIndex(tag)).replaceAll("&quot;", "\"")).append("\n");
                        }
                        items.add(item.toString());
                    }
                }
                return items;
            }
        }.execute(!text.equals("") ? text : DEFAULT_NEWS_URL);
    }
}
