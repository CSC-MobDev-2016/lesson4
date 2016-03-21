package ru.ppzh.lesson4;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FeedActivity extends AppCompatActivity {
    private ListView newsList;
    private NewsAdapter adapter;
    private MatrixCursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        newsList = (ListView) findViewById(R.id.news_list);
        String url = getIntent().getStringExtra(MainActivity.RSS_ADDRESS_EXTRA);
        new AsyncTask<String, Void, Article[]>() {

            @Override
            protected Article[] doInBackground(String... params) {
                RssProvider provider = new RssProvider();
                cursor = (MatrixCursor) provider.query(Uri.parse(params[0]), null, null, null, null);
                Article[] articles = getArticles(cursor);
                cursor.close();
                return articles;
            }

            private Article[] getArticles(Cursor cursor) {
                int count = cursor.getCount();
                cursor.moveToNext();
                Article[] article = new Article[count];
                for (int i = 0; i < count; ++i) {
                    article[i] = new Article(
                            cursor.getString(cursor.getColumnIndex(RssProvider.TITLE)),
                            cursor.getString(cursor.getColumnIndex(RssProvider.DATE)),
                            cursor.getString(cursor.getColumnIndex(RssProvider.DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(RssProvider.NEWS_URL))
                    );
                    cursor.moveToNext();
                }
                return article;
            }

            @Override
            protected void onPostExecute(Article[] articles) {
                adapter = new NewsAdapter(articles, getApplicationContext());
                newsList.setAdapter(adapter);
            }
        }.execute(url);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        ((Article) adapter.getItem(position)).getUrl()
                ));
                startActivity(i);
            }
        });
    }


}
