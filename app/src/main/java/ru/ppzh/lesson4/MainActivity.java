package ru.ppzh.lesson4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String RSS_ADDRESS_EXTRA = "ru.ppzh.lesson4.addrres";
    private Button goButton;
    private EditText rssEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goButton = (Button) findViewById(R.id.go_button);
        rssEditText = (EditText) findViewById(R.id.rss_address);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = rssEditText.getText().toString().trim();
                Intent i = new Intent(getApplicationContext(), FeedActivity.class);
                i.putExtra(RSS_ADDRESS_EXTRA, address);
                startActivity(i);
            }
        });
    }
}
