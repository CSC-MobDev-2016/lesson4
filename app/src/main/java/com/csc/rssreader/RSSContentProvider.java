package com.csc.rssreader;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.net.URL;
import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

public class RSSContentProvider extends ContentProvider {
    final static int COLUMN_DATE = 0;
    final static int COLUMN_TITLE = 1;
    final static int COLUMN_DESCRIPTION = 2;
    public RSSContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(new String[]{"date", "title", "description"});
        try {
            URL urlObj = new URL(selection);
            RssFeed feed = RssReader.read(urlObj);
            ArrayList<RssItem> rssItems = feed.getRssItems();
            for (int i = 0; i < rssItems.size(); i++) {
                cursor.newRow()
                        .add(rssItems.get(i).getPubDate())
                        .add(rssItems.get(i).getTitle())
                        .add(rssItems.get(i).getDescription());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
