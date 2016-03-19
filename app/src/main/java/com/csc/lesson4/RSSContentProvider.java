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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RSSContentProvider extends ContentProvider {

    public static final Uri URI = Uri.parse("content://" + "com.csc.lesson4.rsscontentprovider");
    private String address;
    private String[] projection;

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
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String address, String[] selectionArgs, String sortOrder) {
        MatrixCursor matrixCursor = new MatrixCursor(projection);
        this.address = address;
        this.projection = projection;
        List<HashMap<String, String>> XMLlist = new ArrayList<>();
        try {
            XMLlist = new RSSXMLfetcher().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (HashMap xmllist : XMLlist) {
            String pubDate = (String) xmllist.get("pubDate");
            Log.e("SSS", pubDate);
            String title = (String) xmllist.get("title");
            Log.e("SSS", title);
            String description = (String) xmllist.get("description");
            Log.e("SSS", description);
            matrixCursor.addRow(new String[]{pubDate, title, description});
        }
        if (matrixCursor == null) {
            Log.d("M", "kkkk");
        }
        return matrixCursor;
        // TODO: Implement this to handle query requests from clients.
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class RSSXMLfetcher extends AsyncTask<Void, Void, List<HashMap<String, String>>> {

        private XmlPullParserFactory xmlFactoryObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(Void... adress) {
            // Create a new HttpClient and Post Header
            List<HashMap<String, String>> list = new ArrayList<>();
            String all = "";
            try {
                //Log.e("AAAAA", "Startd download");
                URL url = new URL(address);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //Log.e("AAAAA", "connected");
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/xml");
                //Log.e("AAAAA", "GET");

                conn.connect();
                InputStream stream = conn.getInputStream();

                xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser myparser = xmlFactoryObject.newPullParser();

                myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                myparser.setInput(stream, null);

                list = parseXMLAndStoreIt(myparser);
                stream.close();
            } catch (Exception e) {
            }

            //Log.e("AAAAA2", "Finished download");
            return list;
        }

        public List<HashMap<String, String>> parseXMLAndStoreIt(XmlPullParser myParser) {

            List<HashMap<String, String>> XMLlist = new ArrayList<>();
            int event;
            String text = "";

            try {
                event = myParser.getEventType();

                HashMap<String, String> map = new HashMap<>();

                while (event != XmlPullParser.END_DOCUMENT) {
                    String name = myParser.getName();

                    switch (event) {
                        case XmlPullParser.START_TAG:
                            break;

                        case XmlPullParser.TEXT:
                            text = myParser.getText();
                            break;

                        case XmlPullParser.END_TAG:

                            for (String tagName : projection) {
                                if (name.equals(tagName)) {
                                    map.put(tagName, text);
                                }
                            }
                            if (name.equals("item")) {
                                XMLlist.add(map);
                                map = new HashMap<>();
                            }

                            break;
                    }

                    event = myParser.next();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            for (HashMap<String, String> a : XMLlist) {
                for (String key : projection) {
                    Log.e("XMLlist", a.get(key));
                }
                Log.e("XMLlist", "\n");
            }
            return XMLlist;
        }

    }
}
