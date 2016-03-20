package com.csc.lesson4;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
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
    ListView listView;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_activity);
        String url = getIntent().getStringExtra(MainActivity.URL);
        listView = (ListView) findViewById(R.id.listView);
        Context context = this;
        getArticles(url, context);

    }

    private void getArticles(String url, final Context context) {
        new AsyncTask<String, Void, ArrayList<Article>>() {
            @Override
            protected void onPostExecute(ArrayList<Article> result) {
                super.onPostExecute(result);
                articles = result;
                adapter = new ListViewAdapter(context, result);
                listView.setAdapter(adapter);
            }
            @Override
            protected ArrayList<Article> doInBackground(String... params) {
                ArrayList<Article> articleList = new ArrayList<>();
                try {
                    Cursor cursor = getContentResolver().query(Uri.parse(RSSContentProvider.uri), columnNames, params[0], null, null, null);

                    while (cursor.moveToNext()) {
                        Article article = new Article();
                        article.setData(cursor);
                        articleList.add(article);
                    }
                    cursor.close();
                } catch (Exception e) {}
                return articleList;
            }
        }.execute(url);
    }
}
