package test;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.csc.shmakov.simple_rss_reader.apis.rss.RssParser;
import com.csc.shmakov.simple_rss_reader.model.Channel;
import com.csc.shmakov.simple_rss_reader.model.Item;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class Tests extends ApplicationTestCase<Application> {
     private static final String TYPICAL_RESPONSE =
             "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                     "<?xml-stylesheet title=\"XSL_formatting\" type=\"text/xsl\" href=\"/shared/bsp/xsl/rss/nolsol.xsl\"?>\n" +
                     "\n" +
                     "<rss xmlns:media=\"http://search.yahoo.com/mrss/\" xmlns:atom=\"http://www.w3.org/2005/Atom\" version=\"2.0\">  \n" +
                     "  <channel> \n" +
                     "    <title>BBC News - Home</title>  \n" +
                     "    <link>http://www.bbc.co.uk/news/front_page</link>  \n" +
                     "    <description>The latest stories from the Home section of the BBC News web site.</description>  \n" +
                     "    <language>en-gb</language>  \n" +
                     "    <lastBuildDate>Sat, 19 Mar 2016 10:37:17 GMT</lastBuildDate>  \n" +
                     "    <copyright>Copyright: (C) British Broadcasting Corporation, see http://news.bbc.co.uk/2/hi/help/rss/4498287.stm for terms and conditions of reuse.</copyright>  \n" +
                     "    <image> \n" +
                     "      <url>http://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif</url>  \n" +
                     "      <title>BBC News - Home</title>  \n" +
                     "      <link>http://www.bbc.co.uk/news/front_page</link>  \n" +
                     "      <width>120</width>  \n" +
                     "      <height>60</height> \n" +
                     "    </image>  \n" +
                     "    <ttl>15</ttl>  \n" +
                     "    <atom:link href=\"http://feeds.bbci.co.uk/news/rss.xml\" rel=\"self\" type=\"application/rss+xml\"/>  \n" +
                     "    <item> \n" +
                     "      <title>Crabb takes over as Duncan Smith quits</title>  \n" +
                     "      <description>Stephen Crabb is appointed as work and pensions secretary, replacing Iain Duncan Smith, who resigned on Friday in protest at planned cuts to disability benefits.</description>  \n" +
                     "      <link>http://www.bbc.co.uk/news/uk-politics-35850932#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa</link>  \n" +
                     "      <guid isPermaLink=\"false\">http://www.bbc.co.uk/news/uk-politics-35850932</guid>  \n" +
                     "      <pubDate>Sat, 19 Mar 2016 10:50:12 GMT</pubDate>  \n" +
                     "      <media:thumbnail width=\"66\" height=\"49\" url=\"http://c.files.bbci.co.uk/DACF/production/_88851065_breaking_image_large-3.png\"/>  \n" +
                     "      <media:thumbnail width=\"144\" height=\"81\" url=\"http://c.files.bbci.co.uk/101DF/production/_88851066_breaking_image_large-3.png\"/> \n" +
                     "    </item>  \n" +
                     "    <item> \n" +
                     "      <title>Suicide bomber hits central Istanbul</title>  \n" +
                     "      <description>A suicide bomber hits a major shopping area in the Turkish city of Istanbul, killing four people and wounding up to 20, officials say.</description>  \n" +
                     "      <link>http://www.bbc.co.uk/news/world-europe-35850625#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa</link>  \n" +
                     "      <guid isPermaLink=\"false\">http://www.bbc.co.uk/news/world-europe-35850625</guid>  \n" +
                     "      <pubDate>Sat, 19 Mar 2016 10:34:00 GMT</pubDate>  \n" +
                     "      <media:thumbnail width=\"66\" height=\"49\" url=\"http://c.files.bbci.co.uk/3CF5/production/_88850651_breaking_image_large-3.png\"/>  \n" +
                     "      <media:thumbnail width=\"144\" height=\"81\" url=\"http://c.files.bbci.co.uk/6405/production/_88850652_breaking_image_large-3.png\"/> \n" +
                     "    </item>  \n" +
                     "    <item> \n" +
                     "      <title>Dozens killed in Russia plane crash</title>  \n" +
                     "      <description>A passenger jet has crashed in the southern Russian city of Rostov-on-Don killing all 62 people on board, officials say.</description>  \n" +
                     "      <link>http://www.bbc.co.uk/news/world-europe-35850167#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa</link>  \n" +
                     "      <guid isPermaLink=\"false\">http://www.bbc.co.uk/news/world-europe-35850167</guid>  \n" +
                     "      <pubDate>Sat, 19 Mar 2016 10:31:51 GMT</pubDate>  \n" +
                     "      <media:thumbnail width=\"66\" height=\"49\" url=\"http://c.files.bbci.co.uk/163E/production/_88849650_breaking_image_large-3.png\"/>  \n" +
                     "      <media:thumbnail width=\"144\" height=\"81\" url=\"http://c.files.bbci.co.uk/3D4E/production/_88849651_breaking_image_large-3.png\"/> \n" +
                     "    </item>  \n" +
                     "  </channel> \n" +
                     "</rss>\n";

     public Tests() {
          super(Application.class);
     }

     @Test
     public void testResponseParsing() throws Exception {
         List<Item> items = new RssParser().parse(TYPICAL_RESPONSE).items;
         Assert.assertEquals(3, items.size());
         checkItem(items.get(0),
                 "Crabb takes over as Duncan Smith quits",
                 "Stephen Crabb is appointed as work and pensions secretary, replacing Iain Duncan Smith, who resigned on Friday in protest at planned cuts to disability benefits.",
                 "Sat, 19 Mar 2016 10:50:12 GMT");
         checkItem(items.get(1),
                 "Suicide bomber hits central Istanbul",
                 "A suicide bomber hits a major shopping area in the Turkish city of Istanbul, killing four people and wounding up to 20, officials say.",
                 "Sat, 19 Mar 2016 10:34:00 GMT");
         checkItem(items.get(2),
                 "Dozens killed in Russia plane crash",
                 "A passenger jet has crashed in the southern Russian city of Rostov-on-Don killing all 62 people on board, officials say.",
                 "Sat, 19 Mar 2016 10:31:51 GMT");
     }

    @Test
    public void testCursorToChannelAndBack() throws Exception {
        Item item1 = new Item("title1", "description1", "date1");
        Item item2 = new Item("title2", "description2", "date2");
        Item item3 = new Item("title3", "description3", "date3");
        List<Item> items = Arrays.asList(item1, item2, item3);
        Channel channel = new Channel(items);

        Channel restoredChannel = Channel.fromCursor(channel.toCursor());

        List<Item> restoredItems = restoredChannel.items;
        Assert.assertEquals(3, restoredItems.size());
        for (int i = 0; i < items.size(); i++) {
            checkItem(restoredItems.get(i), items.get(i));
        }
    }

    private void checkItem(Item item, Item expected) {
        checkItem(item, expected.title, expected.description, expected.date);
    }

    private void checkItem(Item item, String title, String description, String date) {
        Assert.assertEquals(title, item.title);
        Assert.assertEquals(description, item.description);
        Assert.assertEquals(date, item.date);
    }

}
