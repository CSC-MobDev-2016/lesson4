package com.csc.smax.reader;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class SmaxRSSReaderContentProvider extends ContentProvider {

    public static final Uri URI = Uri.parse("content://" + "com.csc.smax.reader");

    private static final String ITEM = "item";
    private static final String TITLE = "title";
    private static final String DATE = "pubDate";
    private static final String DESCRIPTION = "description";


    public SmaxRSSReaderContentProvider() {
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
        MatrixCursor cursor = new MatrixCursor(projection);

        try {
            URL url = new URL(selection);
            URLConnection conn = url.openConnection();
            conn.connect();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(conn.getInputStream(), null);

            boolean isItem = false;
            String title = "Example";
            String context = "Empty context";
            String date = "Unknown date";
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals(ITEM)) {
                        isItem = true;
                    } else if (parser.getName().equals(TITLE) && isItem) {
                        title = parser.nextText();
                    } else if (parser.getName().equals(DATE) && isItem) {
                        date = parser.nextText();
                    } else if (parser.getName().equals(DESCRIPTION) && isItem) {
                        context = parser.nextText();
                    }
                } else if (eventType == XmlPullParser.END_TAG && parser.getName().equals(ITEM)) {
                    cursor.addRow(new String[]{title, context, date});
                    isItem = false;
                    title = "Example";
                    context = "Empty context";
                    date = "Unknown date";
                }
                eventType = parser.next();
            }

        } catch (IOException | XmlPullParserException e) {
            Log.d("Content Provider", e.getMessage());
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
