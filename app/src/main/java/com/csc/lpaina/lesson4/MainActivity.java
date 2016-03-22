package com.csc.lpaina.lesson4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    public static final String QUERY_TAG = "QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Button button = (Button) findViewById(R.id.rss_button);
        final EditText editText = (EditText) findViewById(R.id.rss_query);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String query = editText != null ? editText.getText().toString() : null;
                    Intent intent = new Intent(getApplicationContext(), ReadRssActivity.class);
                    intent.putExtra(QUERY_TAG, query);
                    startActivity(intent);
                }
            });
        }
    }
}
