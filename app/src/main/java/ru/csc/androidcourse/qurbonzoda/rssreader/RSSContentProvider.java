package ru.csc.androidcourse.qurbonzoda.rssreader;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class RSSContentProvider extends ContentProvider {
    public static final Uri URI = Uri.parse("content://" + "ru.csc.androidcourse.qurbonzoda.rssreader.provider");

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
        // TODO: Implement this to handle query requests from clients.

        Log.d("query", selection);
        MatrixCursor cursor = new MatrixCursor(projection);

        try {

            URL url = new URL(selection);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();


            xpp.setInput(url.openStream(), null);

            String[] row = new String[projection.length];

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = xpp.getName();
                if (name == null) {
                    name = "";
                }
                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        Arrays.fill(row, "");
                    }

                    for (int i = 0; i < projection.length; i++) {
                        String attribute = projection[i];
                        if (name.equalsIgnoreCase(attribute)) {
                            row[i] = xpp.nextText();
                        }

                    }

                } else if (eventType == XmlPullParser.END_TAG && name.equalsIgnoreCase("item")) {
                    cursor.addRow(row);
                }

                eventType = xpp.next();
            }

        } catch (Exception ignored) {
            Log.d("query", "Exception: " + ignored.getMessage());
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
