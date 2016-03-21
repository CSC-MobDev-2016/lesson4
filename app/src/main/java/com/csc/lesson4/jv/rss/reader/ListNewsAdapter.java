package com.csc.lesson4.jv.rss.reader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListNewsAdapter extends BaseAdapter {

    private ArrayList<RSSitem> rssItems;
    private LayoutInflater layoutInflater;

    public ListNewsAdapter(Context context, ArrayList<RSSitem> rssItems) {

        this.rssItems = rssItems;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return rssItems.size();
    }

    public Object getItem(int position) {
        return rssItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView pubdateTextView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (convertView == null) {
            v = layoutInflater.inflate(R.layout.news_item, null);
            holder = new ViewHolder();

            holder.titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            holder.descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);
            holder.pubdateTextView = (TextView) v.findViewById(R.id.pubdateTextView);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        RSSitem item = rssItems.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.pubdateTextView.setText(item.getPubdate());

        return v;
    }
}
