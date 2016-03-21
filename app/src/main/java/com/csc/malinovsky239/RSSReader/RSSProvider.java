package com.csc.malinovsky239.RSSReader;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class RSSProvider extends ContentProvider {
    private MatrixCursor topics;
    private TitlesList parent;

    public RSSProvider() {
    }

    public RSSProvider(TitlesList parent) {
        topics = new MatrixCursor(new String[]{"title", "pubDate", "description"});
        this.parent = parent;
    }

    public void callUpdate() {
        parent.update();
    }

    public MatrixCursor getTopics() {
        return topics;
    }

    public void setTopics(MatrixCursor topics) {
        this.topics = topics;
    }

    public void addRssChannel(String address) {
        AsyncRSSPageAccess downloadXML = new AsyncRSSPageAccess(this);
        try {
            downloadXML.execute(new URL(address));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void parseXML(String xml) {
        rssXMLParser parser = new rssXMLParser(this);
        parser.execute(xml);
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
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
