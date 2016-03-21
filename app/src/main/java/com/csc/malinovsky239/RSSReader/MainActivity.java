package com.csc.malinovsky239.RSSReader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.csc.malinovsky239.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent i = new Intent(this, TitlesList.class);
        i.putExtra("URL", ((EditText) findViewById(R.id.url)).getText().toString());
        startActivity(i);
    }
}
