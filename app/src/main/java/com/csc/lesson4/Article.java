package com.csc.lesson4;

import android.database.Cursor;

/**
 * Created by anastasia on 19.03.16.
 */
public class Article {
    String title;
    String date;
    String text;

    public Article() {
        this.date = "";
        this.text= "";
        this.title = "";
    }
    void setData(Cursor cursor) {
        int titleIndex = cursor.getColumnIndexOrThrow("title");
        int dateIndex = cursor.getColumnIndexOrThrow("date");
        int textIndex = cursor.getColumnIndexOrThrow("text");
        this.date = cursor.getString(dateIndex);
        this.text= cursor.getString(textIndex);
        this.title = cursor.getString(titleIndex);
    }

    public Article(String title, String date, String text) {
        this.text = text;
        this.title = title;
        this.date = date;
    }
}
