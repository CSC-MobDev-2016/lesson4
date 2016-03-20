package com.csc.my_rss_reader_01;

/**
 * Created by Филипп on 20.03.2016.
 */
public class RssData {
    public String date;
    public String title;
    public String description;

    public RssData() {}
    public RssData(String date, String title, String description) {
        this.date = date;
        this.title = title;
        this.description = description;
    }
}
