package com.csc.shmakov.simple_rss_reader.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.LruCache;

import com.csc.shmakov.simple_rss_reader.apis.rss.RssParser;
import com.csc.shmakov.simple_rss_reader.model.Channel;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RssContentProvider extends ContentProvider {
    public static final Uri AUTHORITY = Uri.parse("content://com.csc.shmakov.rss");

    public static final String RSS_URL_PARAMETER = "rss_url";

    public static final String USE_CACHE_PARAMETER = "use_cache";

    private final OkHttpClient client = new OkHttpClient();

    private final LruCache<String, Cursor> cache = new LruCache<>(5);

    public RssContentProvider() {}

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean onCreate() {
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String url = uri.getQueryParameter(RSS_URL_PARAMETER);
        boolean useCache = Boolean.valueOf(uri.getQueryParameter(USE_CACHE_PARAMETER));
        if (useCache) {
            Cursor cached = cache.get(url);
            if (cached != null) {
                return cached;
            }
        }

        try {
            Cursor result = parseResponse(client.newCall(buildRequest(url)).execute()).toCursor();
            cache.put(url, result);
            return result;
        } catch (IOException e) {
            throw new ProviderException(ProviderException.Type.NO_CONNECTION, e);
        }
    }

    private Request buildRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    private Channel parseResponse(Response response)  {
        if (response.code() != 200) {
            throw new ProviderException(ProviderException.Type.PARSE_ERROR);
        }
        try {
            return new RssParser().parse(response.body().string());
        } catch (IOException | XmlPullParserException e) {
            throw new ProviderException(ProviderException.Type.PARSE_ERROR, e);
        }
    }

}
