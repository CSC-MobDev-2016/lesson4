package com.csc.rssreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Oleg on 21.03.2016.
 */
public class ListArticlesArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] articleDescriptions;
    private final String[] articleTitles;
    private final String[] articleDates;

    public ListArticlesArrayAdapter(Context context, String[] dates, String[] titles, String[] descriptions) {
        super(context, R.layout.article_list_item, titles);
        this.context = context;
        articleDates = dates;
        articleTitles = titles;
        articleDescriptions = descriptions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.article_list_item, parent, false);
        TextView dateText = (TextView) rowView.findViewById(R.id.row_date);
        TextView tileText = (TextView) rowView.findViewById(R.id.row_title);
        TextView descriptionText = (TextView) rowView.findViewById(R.id.row_description);
        dateText.setText(articleDates[position]);
        tileText.setText(articleTitles[position]);
        descriptionText.setText(articleDescriptions[position]);
        return rowView;
    }
}
