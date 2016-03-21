package ru.csc.androidcourse.qurbonzoda.rssreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by qurbonzoda on 21.03.16.
 */
public class ArticleViewAdapter extends ArrayAdapter<Article> {
    public ArticleViewAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.article_layout, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.titleTextView);
            viewHolder.pubDate = (TextView) convertView.findViewById(R.id.dateTextView);
            viewHolder.description = (TextView) convertView.findViewById(R.id.textTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(article.title);
        viewHolder.pubDate.setText(article.pubDate);
        viewHolder.description.setText(article.description);

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView pubDate;
        TextView description;
    }

    public static final String TAG = "ArticleViewAdapter";
}