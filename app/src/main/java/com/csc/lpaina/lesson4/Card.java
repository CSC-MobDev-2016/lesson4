package com.csc.lpaina.lesson4;

public class Card implements Cloneable {
    private String title;
    private String description;
    private String channel;
    private String link;

    public static final String TITLE_NAME = "TITLE";
    public static final String DESCRIPTION_NAME = "DESCRIPTION";
    public static final String CHANNEL_NAME = "CHANNEL";
    public static final String LINK_NAME = "LINK";
    public static final String[] NAMES = new String[]{TITLE_NAME, DESCRIPTION_NAME, CHANNEL_NAME, LINK_NAME};

    public Card() {

    }

    public Card(String title, String description, String channel, String link) {
        this.title = title;
        this.description = description;
        this.channel = channel;
        this.link = link;
    }

    @Override
    protected Card clone() throws CloneNotSupportedException {
        super.clone();
        return new Card(title, description, channel, link);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
