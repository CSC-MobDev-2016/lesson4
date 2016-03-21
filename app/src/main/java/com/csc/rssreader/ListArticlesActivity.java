package com.csc.rssreader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

public class ListArticlesActivity extends AppCompatActivity {

    private String url;
    private ListView listViewArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_articles);
        url = getIntent().getStringExtra(GlobalContext.KEY_URL);
        if (url == null || url.isEmpty()) {
            url = getString(R.string.default_rss);
        }
        listViewArticles = (ListView) findViewById(R.id.list_articles);
        new RSSLoadTask(this).execute();
    }

    private static class RSSLoadTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<ListArticlesActivity> listArticlesActivityRef;
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        public RSSLoadTask(ListArticlesActivity activity) {
            listArticlesActivityRef= new WeakReference<ListArticlesActivity>(activity);
        }
        @Override
        protected Void doInBackground(Void... params) {
            ListArticlesActivity activity = listArticlesActivityRef.get();
            if (activity == null) {
                return null;
            }
            Cursor cursor = null;
            try {
                cursor = activity.getContentResolver().query(Uri.parse("content://com.csc.rssreader.rssloader"), null, activity.url, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        dates.add(cursor.getString(RSSContentProvider.COLUMN_DATE));
                        titles.add(cursor.getString(RSSContentProvider.COLUMN_TITLE));
                        descriptions.add(cursor.getString(RSSContentProvider.COLUMN_DESCRIPTION));
                    }
                }
            } catch (Exception ex) {
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ListArticlesActivity activity = listArticlesActivityRef.get();

            if (activity != null && dates.size() > 0) {
                final String[] articleDates = new String[dates.size()];
                final String[] articleTitles = new String[titles.size()];
                final String[] articleDescriptions = new String[descriptions.size()];
                for (int i = 0; i < dates.size(); i++) {
                    articleDates[i] = "";
                    articleTitles[i] = "";
                    articleDescriptions[i] = "";
                    if (dates.get(i) != null) {
                        articleDates[i] += Html.fromHtml("[" + dates.get(i) + "]");
                    }
                    if (titles.get(i) != null) {
                        articleTitles[i] += titles.get(i);
                    }
                    if (descriptions.get(i) != null) {
                        articleDescriptions[i] += descriptions.get(i);
                    }
                }
                ListArticlesArrayAdapter adapter = new ListArticlesArrayAdapter(activity, articleDates, articleTitles, articleDescriptions);
                activity.listViewArticles.setAdapter(adapter);
            }
            else {
                if (activity != null)
                Toast.makeText(activity, activity.getString(R.string.error), Toast.LENGTH_LONG).show();
            }
        }
    }
}
