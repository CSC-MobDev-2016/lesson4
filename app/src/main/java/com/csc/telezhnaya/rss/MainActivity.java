package com.csc.telezhnaya.rss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        EditText editText = (EditText) findViewById(R.id.url);
        Intent intent = new Intent(this, RSSActivity.class);
        String text = editText.getText().toString();
        if (!text.isEmpty()) {
            intent.putExtra(URL, text);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please give a link", Toast.LENGTH_SHORT).show();
        }
    }
}
