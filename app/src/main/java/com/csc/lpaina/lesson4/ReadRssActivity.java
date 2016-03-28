package com.csc.lpaina.lesson4;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import hugo.weaving.DebugLog;

public class ReadRssActivity extends Activity {

    @Override
    @DebugLog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String query = intent.getStringExtra(MainActivity.QUERY_TAG);

        setContentView(R.layout.activity_read_rss);
        TextView textView = (TextView) findViewById(R.id.rss_feed_name);
        textView.setText(query);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Cursor cursor = getContentResolver().query(MyContentProvider.URI, Card.NAMES, query, null, null);
        if (cursor == null) {
            Intent intentReturn = new Intent(this, MainActivity.class);
            intentReturn.putExtra(MainActivity.ERROR_TAG, true);
            startActivity(intentReturn);
            finish();
        } else {
            recyclerView.setAdapter(new RVAdapter(cursor));
        }

    }

}
