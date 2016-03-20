package com.csc.my_rss_reader_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    protected EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        editText = (EditText) findViewById(R.id.input);
        findViewById(R.id.go_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setText(editText.getHint());
                }
                getResult();
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    getResult();
                }
                return false;
            }
        });

    }

    protected void setContentView() {
        setContentView(R.layout.main);
    }

    private void getResult() {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra(ResultActivity.INPUT, editText.getText().toString());
        startActivity(intent);
    }
}
