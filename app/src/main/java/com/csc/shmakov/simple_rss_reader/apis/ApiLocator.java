package com.csc.shmakov.simple_rss_reader.apis;

import com.csc.shmakov.simple_rss_reader.apis.rss.RssApi;

/**
 * Created by Pavel on 3/12/2016.
 */
public class ApiLocator {
    public final static RssApi RSS_SERVICE = new RssApi();

    private ApiLocator() {}
}
