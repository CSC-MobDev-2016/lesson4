package com.csc.lpaina.lesson4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import hugo.weaving.DebugLog;

public class ReadRssActivity extends Activity {

    private static final String TAG = "ReadRssActivity";

    @Override
    @DebugLog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.QUERY_TAG);

        RSSParserTask task = new RSSParserTask();
        task.execute(query);
        List<Card> cards = null;
        try {
            cards = task.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Log.e(TAG, "onCreate: ", e);
        }

        if (cards == null || cards.isEmpty()) {
            Intent intentReturn = new Intent(this, MainActivity.class);
            intentReturn.putExtra(MainActivity.ERROR_TAG, true);
            startActivity(intentReturn);
            finish();
        }
        setContentView(R.layout.activity_read_rss);
        TextView textView = (TextView) findViewById(R.id.rss_feed_name);
        textView.setText(query);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(new RVAdapter(cards));
    }

}
