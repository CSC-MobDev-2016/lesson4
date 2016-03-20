package com.csc.shmakov.simple_rss_reader.apis.rss;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.csc.shmakov.simple_rss_reader.apis.common.ApiMethodService;
import com.csc.shmakov.simple_rss_reader.apis.common.ApiRequestResult;
import com.csc.shmakov.simple_rss_reader.content_provider.RssContentProvider;
import com.csc.shmakov.simple_rss_reader.model.Channel;

import java.util.concurrent.Callable;

/**
 * Created by Pavel on 3/7/2016.
 */
public class RssApi extends ApiMethodService<Channel> {

    public void fetch(final Context context, final String url) {
        fetchFromProvider(context, url, false);
    }

    public void fetchCached(final Context context, final String url) {
        fetchFromProvider(context, url, true);
    }

    public void fetchFromProvider(final Context context, final String url,
                                                       final boolean useCache)  {
        launchRequest(new Callable<ApiRequestResult<Channel>>() {
            @Override
            public ApiRequestResult<Channel> call() throws Exception {
                Uri uri = RssContentProvider.AUTHORITY.buildUpon()
                        .appendQueryParameter(RssContentProvider.RSS_URL_PARAMETER, url)
                        .appendQueryParameter(RssContentProvider.USE_CACHE_PARAMETER, String.valueOf(useCache))
                        .build();

                Cursor cursor;
                try {
                    cursor = context.getContentResolver().query(uri, null, null, null, null);
                } catch (Exception e) {
                    return new ApiRequestResult.Error<>(e);
                }

                if (cursor == null) {
                    return new ApiRequestResult.Error<>(new Exception("Content provider query failed"));
                }
                try {
                    return new ApiRequestResult.Success<>(Channel.fromCursor(cursor));
                } catch (Exception e) {
                    return new ApiRequestResult.Error<>(new Exception("Failed to unpack from cursor", e));
                } finally {
                    cursor.close();
                }
            }
        });
    }
}
