package com.csc.malinovsky239.RSSReader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemAdapter extends ArrayAdapter<ListItem> {

    public ListItemAdapter(Context context, ArrayList<ListItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
        TextView itemPubDate = (TextView) convertView.findViewById(R.id.itemPubDate);
        TextView itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
        itemTitle.setText(item.getTitle());
        itemPubDate.setText(item.getPubDate());
        itemDescription.setText(item.getDescription());
        return convertView;
    }
}
