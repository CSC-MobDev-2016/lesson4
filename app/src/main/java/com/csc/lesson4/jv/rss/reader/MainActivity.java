package com.csc.lesson4.jv.rss.reader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String INPUT_URI = "com.csc.lesson4.jv.rss.reader.MainActivity.rss_uri";
    private static final String DEFAULT_URL = "http://ria.ru/export/rss2/politics/index.xml";


    private Button btnGo;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGo = (Button) findViewById(R.id.button);
        btnGo.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);
        editText.setHint(DEFAULT_URL);
    }

    @Override
    public void onClick(View v) {

        if (editText.getText().toString().trim().length() == 0) {

            Intent i = new Intent(this, NewsListActivity.class);
            i.putExtra(INPUT_URI, DEFAULT_URL);
            startActivity(i);

            //Toast.makeText(this, R.string.no_url, Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(this, NewsListActivity.class);
        i.putExtra(INPUT_URI, editText.getText().toString());
        startActivity(i);
    }

}
