package com.csc.roman_fedorov.lesson4;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by roman on 20.03.2016.
 */

public class RSSListAdapter extends ArrayAdapter<Item> {
    private Context context;
    private List<Item> values;

    public RSSListAdapter(Context context, List<Item> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    ;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rss_list_item, parent, false);
        TextView titleText = (TextView) rowView.findViewById(R.id.RSSItemTitle);
        titleText.setText(values.get(position).title);
        TextView descriptionText = (TextView) rowView.findViewById(R.id.RSSItemDescription);
        descriptionText.setText(Html.fromHtml(values.get(position).description));
        TextView dateText = (TextView) rowView.findViewById(R.id.RSSItemDate);
        dateText.setText(values.get(position).date);
        return rowView;
    }
}
