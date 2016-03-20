package com.csc.shmakov.simple_rss_reader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.csc.shmakov.simple_rss_reader.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressInputActivity extends AppCompatActivity {

    private static final String DEFAULT_URL = "http://feeds.bbci.co.uk/news/rss.xml";

    @Bind(R.id.url_edit_text) EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_input);
        ButterKnife.bind(this);
        editText.setText(DEFAULT_URL);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    startNewsActivity();
                    return true;
                }
                return false;
            }
        });
    }

    public void onGoClick(View view) {
        startNewsActivity();
    }

    private void startNewsActivity() {
        Intent intent = new Intent(this, NewsListActivity.class);
        intent.putExtra(NewsListActivity.EXTRA_URL, editText.getText().toString());
        startActivity(intent);
    }

}
