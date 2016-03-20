package com.csc.my_rss_reader_01;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Филипп on 20.03.2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ItemViewHolder> {

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView title;
        TextView description;

        ItemViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

    List<RssData> news = Collections.emptyList();
    public void updateNews(List<RssData> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        RssData data = news.get(position);

        holder.date.setText(data.date);
        holder.title.setText(data.title);
        holder.description.setText(data.description);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
