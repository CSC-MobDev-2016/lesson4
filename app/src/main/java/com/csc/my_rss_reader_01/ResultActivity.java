package com.csc.my_rss_reader_01;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends MainActivity {

    public static final String INPUT = "INPUT";
    private static final String DATE_COLUMN = "date";
    private static final String TITLE_COLUMN = "title";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String[] NEWS_DESCRIPTOR = new String[]{DATE_COLUMN, TITLE_COLUMN, DESCRIPTION_COLUMN};

    RecyclerView recyclerView;
    RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RVAdapter();
        recyclerView.setAdapter(adapter);

        String input = getIntent().getStringExtra(INPUT);
        editText.setText(input);

        new GetNewsTask().execute(input.isEmpty() ? editText.getHint().toString() : input);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_result);
    }

    class GetNewsTask extends AsyncTask<String, Void, Cursor> {
        @Override
        protected Cursor doInBackground(String... params) {
            return getContentResolver().query(MyNewsContentProvider.URI, NEWS_DESCRIPTOR, params[0], null, null);
        }

        private String extract(Cursor cursor, String columnName) {
            return cursor.getString(cursor.getColumnIndex(columnName));
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            try {
                List<RssData> news = new ArrayList<>();
                while (cursor.moveToNext()) {
                    news.add(new RssData(extract(cursor, DATE_COLUMN),
                                         extract(cursor, TITLE_COLUMN),
                                         extract(cursor, DESCRIPTION_COLUMN)));
                }
                adapter.updateNews(news);
            } finally {
                cursor.close();
            }
        }
    }

}
