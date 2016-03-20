package com.csc.shmakov.simple_rss_reader.apis.rss;

import com.csc.shmakov.simple_rss_reader.model.Channel;
import com.csc.shmakov.simple_rss_reader.model.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 3/19/2016.
 */
public class RssParser {
    private XmlPullParser xmlParser;
    private int currentEventType;
    private String currentName;
    private String currentText;

    public Channel parse(String xml) throws XmlPullParserException, IOException{
        List<Item> items = new ArrayList<>();

        xmlParser =  XmlPullParserFactory.newInstance().newPullParser();
        xmlParser.setInput(new StringReader(xml));

        makeStep();
        while (currentEventType != XmlPullParser.END_DOCUMENT
                && !atEndTagWithName("channel")) {
            if (atStartTagWithName("item")) {
                items.add(parseItem());
            }
            makeStep();
        }
        return new Channel(items);
    }

    private Item parseItem() throws XmlPullParserException, IOException {
        String title = "";
        String description = "";
        String date = "";
        while (!atEndTagWithName("item")) {
            makeStep();
            if (atStartTagWithName("title")) {
                makeStep();
                title = currentText;
            } else if (atStartTagWithName("description")) {
                makeStep();
                description = currentText;
            } else if (atStartTagWithName("pubDate")) {
                makeStep();
                date = currentText;
            }
        }
        return new Item(title, description, date);
    }

    private void makeStep() throws XmlPullParserException, IOException {
        currentEventType = xmlParser.next();
        currentName = xmlParser.getName();
        currentText = xmlParser.getText();
    }

    private String getAttr(String name) {
        return xmlParser.getAttributeValue(null, name);
    }

    private boolean atStartTagWithName(String name) {
        return currentEventType == XmlPullParser.START_TAG
                && currentName.equals(name);
    }

    private boolean atEndTagWithName(String name) {
        return currentEventType == XmlPullParser.END_TAG
                && currentName.equals(name);
    }
}
