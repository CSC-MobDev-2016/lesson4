package com.csc.lesson4;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by anastasia on 19.03.16.
 */
public class ListActivity extends AppCompatActivity{

    ArrayList<Article> articles = new ArrayList<>();
    String[] columnNames = {"title", "date", "text"};
    private static String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_activity);
        url = getIntent().getStringExtra(MainActivity.URL);
        ArrayList<Article> articles = new ArrayList<>();

        try {
            Cursor cursor = getContentResolver().query(Uri.parse(RSSContentProvider.uri), columnNames, url, null, null, null);

            while (cursor.moveToNext()) {
                Article article = new Article();
                article.setData(cursor);
                articles.add(article);
            }
            cursor.close();
        } catch (Exception e) {}

        ListView listView = (ListView) findViewById(R.id.listView);
        ListViewAdapter adapter = new ListViewAdapter(this, articles);
        listView.setAdapter(adapter);
    }
}
