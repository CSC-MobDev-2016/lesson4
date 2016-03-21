package com.csc.rssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnOK = (Button) findViewById(R.id.btn_ok);
        editTextURL = (EditText) findViewById(R.id.url);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListArticlesActivity.class);
                String url = editTextURL.getText().toString();
                intent.putExtra(GlobalContext.KEY_URL, url);
                startActivity(intent);
            }
        });
    }
}
