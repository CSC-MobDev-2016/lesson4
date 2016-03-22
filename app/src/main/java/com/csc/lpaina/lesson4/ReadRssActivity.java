package com.csc.lpaina.lesson4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

public class ReadRssActivity extends Activity {

    @Override
    @DebugLog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.QUERY_TAG);
        setContentView(R.layout.activity_read_rss);
        TextView textView = (TextView) findViewById(R.id.rss_feed_name);
        textView.setText(query);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Card> cards = new ArrayList<>();
        cards.add(new Card("one", "two"));
        cards.add(new Card("and", "or"));
        cards.add(new Card("that", "this"));
        cards.add(new Card("hey", "hi"));
        cards.add(new Card("you", "me"));
        recyclerView.setAdapter(new RVAdapter(cards));
    }
}
