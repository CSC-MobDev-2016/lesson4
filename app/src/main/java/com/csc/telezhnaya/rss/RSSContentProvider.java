package com.csc.telezhnaya.rss;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;


public class RSSContentProvider extends ContentProvider {
    public static final Uri URI = Uri.parse("content://" + "com.csc.telezhnaya.rss");

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
        MatrixCursor matrixCursor = new MatrixCursor(projection);
        try {
            URL url = new URL(selection);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(connection.getInputStream(), null);

            boolean inItem = false;
            String title = "", text = "", date = "";
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        inItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (inItem)
                            title = xpp.nextText();
                    } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                        if (inItem)
                            date = xpp.nextText();
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (inItem)
                            text = xpp.nextText();
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    inItem = false;
                    matrixCursor.addRow(new String[]{title, date, text});
                    title = "";
                    text = "";
                    date = "";
                }

                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matrixCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
