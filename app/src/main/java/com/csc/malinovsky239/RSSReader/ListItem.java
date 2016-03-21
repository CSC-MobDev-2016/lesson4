package com.csc.malinovsky239.RSSReader;

/**
 * Created by malinovsky239 on 20.03.2016.
 */
public class ListItem {
    private String title;
    private String pubDate;
    private String description;

    ListItem(String title, String pubDate, String description) {
        this.title = title;
        this.pubDate = pubDate;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }
}
