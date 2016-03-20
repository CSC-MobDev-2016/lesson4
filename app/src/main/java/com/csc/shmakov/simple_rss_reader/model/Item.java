package com.csc.shmakov.simple_rss_reader.model;

/**
 * Created by Pavel on 3/19/2016.
 */
public class Item {
    public final String title;
    public final String description;
    public final String date;

    public Item(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
}
