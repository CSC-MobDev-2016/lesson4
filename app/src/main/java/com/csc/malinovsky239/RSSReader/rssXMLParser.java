package com.csc.malinovsky239.RSSReader;

import android.database.MatrixCursor;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class rssXMLParser extends AsyncTask<String, Void, MatrixCursor> {

    private RSSProvider provider;

    public rssXMLParser(RSSProvider provider) {
        this.provider = provider;
    }

    @Override
    protected MatrixCursor doInBackground(String... params) {
        MatrixCursor result = null;

        try {
            int eventType;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            result = new MatrixCursor(new String[]{"title", "pubDate", "description"}, 0);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(params[0]));
            eventType = xpp.getEventType();
            String title = "", pubDate = "", description = "";
            boolean titleSet = false, pubDateSet = false, descriptionSet = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = xpp.getName();
                    if (tagName.equals("title") || tagName.equals("pubDate") || tagName.equals("description")) {
                        try {
                            try {
                                xpp.next();
                            } catch (XmlPullParserException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String text = xpp.getText();
                        if (tagName.equals("title")) {
                            title = text;
                            titleSet = true;
                        }
                        if (tagName.equals("pubDate")) {
                            pubDate = text;
                            pubDateSet = true;
                        }
                        if (tagName.equals("description")) {
                            description = text;
                            descriptionSet = true;
                        }
                        if (titleSet && pubDateSet && descriptionSet) {
                            result.addRow(new String[]{title, pubDate, description});
                            titleSet = false;
                            pubDateSet = false;
                            descriptionSet = false;
                        }
                    }
                }
                eventType = xpp.next();
            }
            return result;
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void onPostExecute(MatrixCursor result) {
        provider.setTopics(result);
        provider.callUpdate();
    }
}
