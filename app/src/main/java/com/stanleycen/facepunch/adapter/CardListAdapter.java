package com.stanleycen.facepunch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haarman.listviewanimations.ArrayAdapter;
import com.stanleycen.facepunch.card.Card;
import com.stanleycen.facepunch.model.ICardItem;

import java.util.List;

/**
 * Created by scen on 2/17/14.
 */
public class CardListAdapter extends ArrayAdapter<Card> {
    public LayoutInflater inflater;
    public Context context;

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public CardListAdapter(Context context, List<Card> items) {
        super(items);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(inflater, convertView, position, context);
    }
}
