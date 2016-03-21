package com.csc.lesson4.jv.rss.reader;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class ContentProvider_1 extends ContentProvider {

    public static final String INPUT_URI = "com.csc.lesson4.jv.rss.reader.MainActivity.rss_uri";

    public static final String AUTHORITY = "com.csc.lesson4.jv.rss.reader.provider1";

    public static final String ITEM_PATH = "items";

    public static final Uri ITEM_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + ITEM_PATH);

    static final int URI_ITEMS = 1;


    private static final UriMatcher uriMatcher;


    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, ITEM_PATH, URI_ITEMS);
    }


    public ContentProvider_1() {
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
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
//        if (uriMatcher.match(uri) != URI_ITEMS)
//            throw new IllegalArgumentException("Wrong URI: " + uri);

        String inputUri = uri.getQueryParameter(INPUT_URI);

        RSSParser parser = new RSSParser(inputUri);
        ArrayList<RSSitem> rssItems = null;
        try {
            rssItems = parser.readItems();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        MatrixCursor cursor = new MatrixCursor(new String[]{RSSParser.TITLE, RSSParser.DESCRIPTION, RSSParser.PUB_DATE});

        if (rssItems != null) {
            for (RSSitem item : rssItems) {
                cursor.addRow(new String[]{item.getTitle(), item.getDescription(), item.getPubdate()});
            }
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
