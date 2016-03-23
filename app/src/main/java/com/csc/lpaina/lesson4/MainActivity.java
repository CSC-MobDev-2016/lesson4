package com.csc.lpaina.lesson4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
        boolean hasError = intent.getBooleanExtra(ERROR_TAG, false);

        setContentView(R.layout.main_activity);
        final TextView textHint = (TextView) findViewById(R.id.text_hint);
        final Button button = (Button) findViewById(R.id.rss_button);
        final EditText editText = (EditText) findViewById(R.id.rss_query);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            hasError = true;
        }

        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String query = editText.getText().toString();
                    if (!(query.startsWith("http://") || query.startsWith("https://"))) {
                        query = "http://" + query;
                    }
                    Intent intentForward = new Intent(getApplicationContext(), ReadRssActivity.class);
                    intentForward.putExtra(QUERY_TAG, query);
                    startActivity(intentForward);
                }
            });
        }
        if (hasError) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
                textHint.setText(R.string.error_internet_hint);
            } else {
                textHint.setText(R.string.error_hint);
            }
        } else {
            textHint.setText("");
        }

    }

}
