package com.csc.lesson4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anastasia on 19.03.16.
 */
public class ListViewAdapter extends ArrayAdapter<Article> {
    public ListViewAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.titleTextView);
            viewHolder.date = (TextView) convertView.findViewById(R.id.dateTextView);
            viewHolder.text = (TextView) convertView.findViewById(R.id.textTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(article.title);
        viewHolder.date.setText(article.date);
        viewHolder.text.setText(article.text);

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView date;
        TextView text;
    }
}
