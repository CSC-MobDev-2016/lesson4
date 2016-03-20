package com.csc.my_rss_reader_01;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyNewsContentProvider extends ContentProvider {

    public static final Uri URI = Uri.parse("content://" + "com.csc.my_rss_reader_01");

    public MyNewsContentProvider() {
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
        for (RssData rssData: getRss(selection)) {
            cursor.addRow(new String[]{rssData.date, rssData.title, rssData.description});
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private List<RssData> getRss(String address) {
        InputStream is = null;
        List<RssData> rssDataList = new ArrayList<>();
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            is = connection.getInputStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, null);

            int eventType = xpp.getEventType();
            RssData rssData = null;
            RSSXMLTag currentTag = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                }
                else if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("item")) {
                        rssData = new RssData();
                        currentTag = RSSXMLTag.IGNORETAG;
                    }
                    else if (xpp.getName().equals("title")) {
                        currentTag = RSSXMLTag.TITLE;
                    }
                    else if (xpp.getName().equals("pubDate")) {
                        currentTag = RSSXMLTag.DATE;
                    }
                    else if (xpp.getName().equals("description")) {
                        currentTag = RSSXMLTag.DESCRIPTION;
                    }
                }
                else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equals("item")) {
                        rssDataList.add(rssData);
                    }
                    else {
                        currentTag = RSSXMLTag.IGNORETAG;
                    }
                }
                else if (eventType == XmlPullParser.TEXT) {
                    String content = xpp.getText().trim();
                    if (rssData != null) {
                        switch (currentTag) {
                            case TITLE:
                                if(content.length() != 0) {
                                    rssData.title = rssData.title == null ? content : rssData.title + content;
                                }
                                break;
                            case DATE:
                                if(content.length() != 0) {
                                    rssData.date = rssData.date == null ? content : rssData.date + content;
                                }
                                break;
                            case DESCRIPTION:
                                if (content.length() != 0) {
                                    rssData.description = rssData.description == null ? content : rssData.description + content;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rssDataList;
    }

    private enum RSSXMLTag {
        TITLE, DATE, DESCRIPTION, IGNORETAG;
    }
}
