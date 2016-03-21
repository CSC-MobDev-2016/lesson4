package ru.csc.androidcourse.qurbonzoda.rssreader;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class ArticlesActivity extends AppCompatActivity {

    ArrayList<Article> articleList;
    ListView listView;
    String[] projection = {Article.TITLE, Article.DESCRIPTION, Article.PUBLICATION_DATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        listView = (ListView) findViewById(R.id.listView);

        String link = getIntent().getStringExtra(MainActivity.RSS_LINK);

        new ArticleLoader().execute(link);

    }

    private class ArticleLoader extends AsyncTask<String, Void, ArrayList<Article>> {

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected ArrayList<Article> doInBackground(String... params) {
            Log.d(TAG, "doInBackground");
            ArrayList<Article> articleList = new ArrayList<>();
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(RSSContentProvider.URI, projection, params[0], null, null, null);

                while (cursor.moveToNext()) {
                    articleList.add(new Article(cursor));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return articleList;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute");
            articleList = result;
            listView.setAdapter(new ArticleViewAdapter(getApplicationContext(), articleList));
        }
    }

    public static final String TAG = "ArticleActivity";
}
