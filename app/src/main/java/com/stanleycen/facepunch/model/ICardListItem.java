package com.stanleycen.facepunch.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.stanleycen.facepunch.adapter.CardListAdapter;

/**
 * Created by scen on 2/17/14.
 */
public interface ICardListItem {
    public abstract boolean isHeader();
    public int getViewType();
    public abstract View getView(LayoutInflater inflater, View convertView, int position, Context context);
}
