package com.csc2016.kharitonov.lesson4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class FirstScreenActivity
        extends AppCompatActivity
        implements TextView.OnEditorActionListener, View.OnClickListener {

    public static final String URL_KEY = "URL_KEY";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        editText = (EditText) findViewById(R.id.editText);
        editText.setOnEditorActionListener(this);
        findViewById(R.id.go_button).setOnClickListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            return startNextActivity();
        }
        return false;
    }

    public boolean startNextActivity() {
        String url = editText.getText().toString();
        if (url.isEmpty())
            url = editText.getHint().toString();
        Intent intent = new Intent(this, PublListActivity.class);
        intent.putExtra(URL_KEY, url);
        startActivity(intent);
        return true;
    }

    @Override
    public void onClick(View v) {
        startNextActivity();
    }
}
