package ru.csc.androidcourse.qurbonzoda.rssreader;

import android.database.Cursor;

/**
 * Created by qurbonzoda on 21.03.16.
 */
public class Article {
    public static final String PUBLICATION_DATE = "pubDate";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";

    String pubDate;
    String title;
    String description;

    public Article(String pubDate, String title, String description) {
        this.pubDate = pubDate;
        this.title = title;
        this.description = description;
    }

    public Article(Cursor cursor) {
        this(cursor.getString(cursor.getColumnIndexOrThrow(PUBLICATION_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
    }

    public static final String TAG = "Article";
}
