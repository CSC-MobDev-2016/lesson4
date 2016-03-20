package com.csc.lesson4;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RSSContentProvider extends ContentProvider {
    public static final String uri = "content://com.csc.lesson4.RSSContentProvider";

    private enum RSSXMLTag {
        TITLE, DATE, LINK, CONTENT, GUID, IGNORETAG;
    }

    public RSSContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        MatrixCursor matrixCursor = new MatrixCursor(projection);
        List<Article> articles = new ArrayList<>();
        try {
            String urlStr = selection;
            InputStream is = null;
            ArrayList<Article> ArticleList = new ArrayList<Article>();
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();
            Log.d("debug", "The response is: " + response);
            is = connection.getInputStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, null);


            boolean insideItem = false;
            String title = "", text = "", date = "", link = "";
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem)
                            title = xpp.nextText();
                    } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                        if (insideItem)
                            date = xpp.nextText();
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem)
                            text = xpp.nextText();
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem)
                            link = xpp.nextText();
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                    ArticleList.add(new Article(title, date, text));
                    title = "";
                    text = "";
                    date = "";
                }

                eventType = xpp.next();
            }

            for (Article article : ArticleList) {
                matrixCursor.addRow(new String[]{article.title, article.date, article.text});
            }
        } catch (Exception e) {
        }
        return matrixCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not implemented");
    }
}
