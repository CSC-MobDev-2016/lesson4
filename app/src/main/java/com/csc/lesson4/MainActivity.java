package com.csc.lesson4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static String URL = "SomeUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void onGoClick(View view) {
        try {
            EditText input = (EditText) findViewById(R.id.editText);

            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra(URL, input.getText().toString());
            startActivity(intent);
        } catch(Exception _) {}
    }
}
