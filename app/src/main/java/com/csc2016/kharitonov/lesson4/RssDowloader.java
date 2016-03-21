package com.csc2016.kharitonov.lesson4;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RssDowloader extends AsyncTask<String, Void, List<RssListViewItem>> {
    private static final String[] PROJECTION = new String[]{"Title", "Date", "Description"};
    private ListView listView;
    private Context context;

    public RssDowloader(Context context, ListView listView) {
        this.listView = listView;
        this.context = context;
    }

    @Override
    protected void onPostExecute(List<RssListViewItem> list) {
        if (list == null) {
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("I cant load RSS").show();
            return;
        }
        listView.setAdapter(new ListViewRssAdapter(context, list));
    }

    @Override
    protected List<RssListViewItem> doInBackground(String... params) {
        Cursor cursor = context
                .getContentResolver()
                .query(RssProvider.URI, PROJECTION, params[0], null, null);

        if (cursor == null)
            return null;

        List<RssListViewItem> res = new ArrayList<>();

        while (cursor.moveToNext()) {
            res.add(new RssListViewItem(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
        return res;
    }
}