package com.csc.lpaina.lesson4;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewName;
        TextView textViewInfo;

        PersonViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            textViewName = (TextView) itemView.findViewById(R.id.feed_name);
            textViewInfo = (TextView) itemView.findViewById(R.id.feed_info);
        }
    }

    List<Card> cards;

    RVAdapter(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        Card card = cards.get(i);
        personViewHolder.textViewName.setText(card.getName());
        personViewHolder.textViewInfo.setText(card.getInfo());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
