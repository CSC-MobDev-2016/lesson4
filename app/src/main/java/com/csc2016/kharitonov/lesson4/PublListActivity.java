package com.csc2016.kharitonov.lesson4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class PublListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publ_list_screen);
        String url = getIntent().getStringExtra(FirstScreenActivity.URL_KEY);
        ((TextView) findViewById(R.id.textViewTitle)).setText(url);

        new RssDowloader(this, (ListView) findViewById(R.id.listPublView)).execute(url);
    }
}
