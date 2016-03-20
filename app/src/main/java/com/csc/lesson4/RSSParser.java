package com.csc.lesson4;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 19.03.2016.
 */

class Item {
    String title;
    String description;
    String date;

    public Item(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
}

class Channel {
    String title;
    String description;
    String link;
    List<Item> data;

    public Channel(String title, String description, String link, List<Item> data) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.data = data;
    }
}

public class RSSParser {
    private static final String ns = null;

    public Channel parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private Channel readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        Channel channel = null;

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("channel")) {
                channel = readChannel(parser);
            } else {
                skip(parser);
            }
        }
        return channel;
    }

    private Channel readChannel(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "channel");

        String title = null;
        String description = null;
        String link = null;
        List<Item> data = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = read(parser, "title");
            } else if (name.equals("description")) {
                description = read(parser, "description");
            } else if (name.equals("link")) {
                link = read(parser, "link");
            } else if (name.equals("item")) {
                data.add(readItem(parser));
            } else {
                skip(parser);
            }
        }
        return new Channel(title, description, link, data);
    }

    private Item readItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        String date = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("title")) {
                title = read(parser, "title");
            } else if (name.equals("description")) {
                description = read(parser, "description");
            } else if (name.equals("pubDate")) {
                date = read(parser, "pubDate");
            } else {
                skip(parser);
            }
        }
        return new Item(title, description, date);
    }

    private String read(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String str = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return str;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
