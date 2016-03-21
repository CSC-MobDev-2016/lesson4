package com.csc.smax.reader;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TextViewHolder> {

    private List<String> rss_texts;

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textView;

        public TextViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            textView = (TextView) itemView.findViewById(R.id.output_text);
        }
    }

    public RVAdapter() {
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_layout, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        String text = rss_texts.get(position);

        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return rss_texts.size();
    }

    public void setRSS(List<String> rss_texts) {
        this.rss_texts = rss_texts;
        notifyDataSetChanged();
    }
}
