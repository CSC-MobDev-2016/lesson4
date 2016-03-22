package com.csc.lpaina.lesson4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    public static final String QUERY_TAG = "QUERY";
    public static final String ERROR_TAG = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //boolean hasError = intent.getBooleanExtra(ERROR_TAG, false);

        setContentView(R.layout.main_activity);
        Button button = (Button) findViewById(R.id.rss_button);
        final EditText editText = (EditText) findViewById(R.id.rss_query);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String query = editText != null ? editText.getText().toString() : null;
                    Intent intentForward = new Intent(getApplicationContext(), ReadRssActivity.class);
                    intentForward.putExtra(QUERY_TAG, query);
                    startActivity(intentForward);
                }
            });
        }
        /*if (hasError){
            TextView textHint = (TextView) findViewById(R.id.text_hint);
            textHint.setText(R.string.error_hint);
        }*/
    }
}
