package ru.csc.androidcourse.qurbonzoda.rssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    private Button goButton;
    private EditText editText;
    public static final String RSS_LINK = "rss_link";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        goButton.setOnClickListener(this);
        editText.setOnEditorActionListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ArticlesActivity.class);
        intent.putExtra(RSS_LINK, editText.getText().toString());
        startActivity(intent);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        goButton.callOnClick();
        return false;
    }
}
