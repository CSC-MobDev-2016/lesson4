package com.csc.lesson4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String RSS_QUERY = "RSS_QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button RSSQueryButton = (Button) findViewById(R.id.RSSQueryButton);
        RSSQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.RSSQuery);
                String query = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, RSSActivity.class);
                intent.putExtra(RSS_QUERY, query);
                startActivity(intent);
            }
        });
    }
}
