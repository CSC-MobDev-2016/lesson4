package com.csc.shmakov.simple_rss_reader.model;

import android.database.Cursor;
import android.database.MatrixCursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pavel on 3/19/2016.
 */
public class Channel {
    public final List<Item> items;

    public Channel(List<Item> items) {
        this.items = Collections.unmodifiableList(items);
    }


    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    public static Channel fromCursor(Cursor cursor) {
        List<Item> items = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            items.add(new Item(getString(cursor, COLUMN_TITLE),
                               getString(cursor, COLUMN_DESCRIPTION),
                               getString(cursor, COLUMN_DATE)));
        }
        return new Channel(items);
    }

    private static String getString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    public Cursor toCursor() {
        MatrixCursor cursor = new MatrixCursor(new String[] {COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_DATE});
        for (Item item: items) {
            cursor.addRow(new String[] {item.title, item.description, item.date});
        }
        return cursor;
    }
}
