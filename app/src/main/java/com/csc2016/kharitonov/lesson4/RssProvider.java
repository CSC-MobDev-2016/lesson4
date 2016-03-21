package com.csc2016.kharitonov.lesson4;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by chainic-vina on 20.03.16.
 */
public class RssProvider extends ContentProvider {
    public static final String PROVIDER_URL = "com.csc2016.kharitonov.lesson4.provider";
    public static final Uri URI = Uri.parse("content://" + PROVIDER_URL);

    public static final String TITLE_TAG = "title";
    public static final String PUB_DATE_TAG = "pubDate";
    public static final String DESCRIPTION_TAG = "description";
    public static final String ITEM_TAG = "item";
    public static final String VOID_STRING = "";

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String rssUrl, String[] selectionArgs, String sortOrder) {
        if (uri == null)
            return null;

        URL url = null;
        try {
            url = new URL(rssUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (url == null)
            return null;

        InputStream inputStream = null;
        MatrixCursor cursor = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputStream != null) {
            XmlPullParserFactory pullParserFactory = null;
            try {
                pullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = pullParserFactory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);

                int event = parser.getEventType();

                cursor = new MatrixCursor(projection);

                String description = VOID_STRING;
                String title = VOID_STRING;
                String pubDate = VOID_STRING;

                String value = null;

                while (event != XmlPullParser.END_DOCUMENT) {

                    switch (event) {
                        case XmlPullParser.START_TAG:
                            break;

                        case XmlPullParser.TEXT:
                            value = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            String name = parser.getName();
                            if (name.equals(TITLE_TAG))
                                title = value;
                            else if (name.equals(PUB_DATE_TAG))
                                pubDate = value;
                            else if (name.equals(DESCRIPTION_TAG))
                                description = value;
                            else if (name.equals(ITEM_TAG)) {
                                cursor.addRow(new String[]{title, pubDate, description});
                                Log.d("my_info", "Title :" + title + " Descr: " + description + " Date: " + pubDate);
                                description = VOID_STRING;
                                title = VOID_STRING;
                                pubDate = VOID_STRING;
                            }
                    }
                    event = parser.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
