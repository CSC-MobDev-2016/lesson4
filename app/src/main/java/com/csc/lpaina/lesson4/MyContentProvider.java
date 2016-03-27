package com.csc.lpaina.lesson4;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MyContentProvider extends ContentProvider {
    public static final Uri URI = Uri.parse("content://" + "com.csc.lpaina.myprovider");
    private static final String TAG = "MyContentProvider";


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(projection);
        RSSParserTask task = new RSSParserTask();
        task.execute(selection);

        try {
            List<Card> cards = task.get(5, TimeUnit.SECONDS);
            for (Card card : cards) {
                cursor.addRow(new String[]{card.getTitle(), card.getDescription(), card.getChannel(), card.getLink()});
            }
        } catch (ExecutionException | InterruptedException | TimeoutException | NullPointerException e) {
            Log.e(TAG, "query: cannot receive data", e);
            return null;
        }

        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
