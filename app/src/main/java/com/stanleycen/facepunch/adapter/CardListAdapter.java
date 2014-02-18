package com.stanleycen.facepunch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haarman.listviewanimations.ArrayAdapter;
import com.stanleycen.facepunch.card.Card;
import com.stanleycen.facepunch.card.CardItemTypes;
import com.stanleycen.facepunch.model.ICardItem;
import com.stanleycen.facepunch.model.ICardListItem;

import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/17/14.
 */
public class CardListAdapter extends ArrayAdapter<ICardListItem> {
    public LayoutInflater inflater;
    public Context context;

    @Override
    public int getViewTypeCount() {
        return CardItemTypes.values().length;
    }

    @DebugLog
    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    public CardListAdapter(Context context, List<ICardListItem> items) {
        super(items);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(inflater, convertView, position, context);
    }
}
