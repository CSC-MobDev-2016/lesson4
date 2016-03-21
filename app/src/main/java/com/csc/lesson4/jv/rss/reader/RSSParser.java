package com.csc.lesson4.jv.rss.reader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class RSSParser {
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String LINK = "link";
    public static final String AUTHOR = "author";
    public static final String ITEM = "item";
    public static final String PUB_DATE = "pubDate";
    public static final String GUID = "guid";

    private URL url;

    public RSSParser(String feedUrl) {
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<RSSitem> readItems() throws ParserConfigurationException, IOException, SAXException {
        ArrayList<RSSitem> rssItems = null;

        if (url == null) {
            return null;
        }

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

            InputStream in = conn.getInputStream();

            rssItems = new ArrayList<>();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();


            Document document = db.parse(in);
            Element element = document.getDocumentElement();

            NodeList itemList = element.getElementsByTagName(ITEM);

            for (int itemInd = 0; itemInd < itemList.getLength(); ++itemInd) {

                Element itemElement = (Element) itemList.item(itemInd);

                String title = getCharacterData(itemElement, TITLE);
                String description = getCharacterData(itemElement, DESCRIPTION);
                String link = getCharacterData(itemElement, LINK);
                String guid = getCharacterData(itemElement, GUID);
                String author = getCharacterData(itemElement, AUTHOR);
                String pubdate = getCharacterData(itemElement, PUB_DATE);

                RSSitem message = new RSSitem();
                message.setAuthor(author);
                message.setDescription(description);
                message.setGuid(guid);
                message.setLink(link);
                message.setTitle(title);
                message.setPubdate(pubdate);

                rssItems.add(message);
            }
        }


        return rssItems;
    }

    private String getCharacterData(Element itemElement, String tagName) {

        NodeList tagList = itemElement.getElementsByTagName(tagName);

        if (tagList.getLength() > 0) {
            return tagList.item(0).getTextContent();
        }
        return "";
    }

}
