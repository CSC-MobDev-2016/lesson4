package com.csc2016.kharitonov.lesson4;

public class RssListViewItem {
    private String title;
    private String date;
    private String description;

    public RssListViewItem(String title, String date, String decryption) {
        this.title = title;
        this.date = date;
        this.description = decryption;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}

