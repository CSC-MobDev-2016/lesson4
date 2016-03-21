package com.csc.malinovsky239.RSSReader;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class AsyncRSSPageAccess extends AsyncTask<URL, Void, String> {

    private RSSProvider provider;

    AsyncRSSPageAccess(RSSProvider provider) {
        this.provider = provider;
    }

    @Override
    protected String doInBackground(URL... params) {
        StringBuilder xmlBuilder = new StringBuilder();
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(params[0].openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                xmlBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlBuilder.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        provider.parseXML(result);
    }
}
