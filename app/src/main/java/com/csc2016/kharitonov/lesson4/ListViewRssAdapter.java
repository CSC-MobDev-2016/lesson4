package com.csc2016.kharitonov.lesson4;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewRssAdapter extends ArrayAdapter {
    LayoutInflater inflater;
    private List<RssListViewItem> list;

    public ListViewRssAdapter(Context context, List<RssListViewItem> list) {
        super(context, R.layout.list_item, list);
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.textViewTitle))
                .setText(Html.fromHtml(list.get(position).getTitle()));
        ((TextView) convertView.findViewById(R.id.textViewDate))
                .setText(Html.fromHtml(list.get(position).getDate()));
        ((TextView) convertView.findViewById(R.id.textViewDescription))
                .setText(Html.fromHtml(list.get(position).getDescription()));

        return convertView;
    }
}