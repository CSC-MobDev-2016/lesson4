package com.csc.lesson4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String ADDRESS = "com.csc.lesson4.ADDRESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        EditText editext = (EditText) findViewById(R.id.word);
        Intent intent = new Intent(this, RSSReaderActivity.class);
        intent.putExtra(ADDRESS, editext.getText().toString());
        startActivity(intent);
    }
}
