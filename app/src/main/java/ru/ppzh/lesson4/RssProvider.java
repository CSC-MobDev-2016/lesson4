package ru.ppzh.lesson4;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class RssProvider extends ContentProvider {
    private static final String TAG = "RssProvider";

    private static final String ITEM = "item";

    public static final String TITLE = "title";
    public static final String DATE = "pubDate";
    public static final String DESCRIPTION = "description";
    public static final String NEWS_URL = "link";

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(new String[]{TITLE, DATE, DESCRIPTION, NEWS_URL});
        String title = "", date = "", description = "", news_url = "";
        Log.i(TAG, "uri: " + selection);
        try {
            URL url = new URL(selection);
            XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();
            xpp.setInput(url.openStream(), null);

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                while ((eventType != XmlPullParser.START_TAG || !xpp.getName().equals(ITEM)) &&
                        (eventType != XmlPullParser.END_DOCUMENT)) {
                    eventType = xpp.next();
                }

                if (eventType == XmlPullParser.END_DOCUMENT)
                    break;

                Log.i(TAG, "tagname: " + xpp.getName());

                while (eventType != XmlPullParser.END_TAG || !xpp.getName().equals(ITEM)) {
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals(TITLE)) {
                        Log.i(TAG, "tagname: " + xpp.getName());
                        xpp.next();
                        title = xpp.getText();
                        Log.i(TAG, "content: " + xpp.getText());
                        xpp.next();
                    }
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals(DATE)) {
                        Log.i(TAG, "tagname: " + xpp.getName());
                        xpp.next();
                        date = xpp.getText();
                        Log.i(TAG, "content: " + xpp.getText());
                        xpp.next();
                    }
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals(DESCRIPTION)) {
                        Log.i(TAG, "tagname: " + xpp.getName());
                        xpp.next();
                        description = xpp.getText();
                        Log.i(TAG, "content: " + xpp.getText());
                        xpp.next();
                    }
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals(NEWS_URL)) {
                        Log.i(TAG, "tagname: " + xpp.getName());
                        xpp.next();
                        news_url = xpp.getText();
                        Log.i(TAG, "content: " + xpp.getText());
                        xpp.next();
                    }
                    eventType = xpp.next();
                }
                cursor.addRow(new Object[]{title, date, description, news_url});
                eventType = xpp.next();
            }

            Log.i(TAG, "parsing finished");
        } catch (Exception e) {
            e.printStackTrace();
            return cursor;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
