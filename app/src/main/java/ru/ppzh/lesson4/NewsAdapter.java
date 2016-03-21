package ru.ppzh.lesson4;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
    private Article[] articles;
    private LayoutInflater inflater;

    public NewsAdapter(Article[] articles, Context context) {
        this.articles = articles;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return articles.length;
    }

    @Override
    public Object getItem(int position) {
        return articles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(articles[position].getTitle());

        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText(articles[position].getDate());

        TextView description = (TextView) view.findViewById(R.id.description);
        description.setText(articles[position].getDescription());

        return view;
    }
}
