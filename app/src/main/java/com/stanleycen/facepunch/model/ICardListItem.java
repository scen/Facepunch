package com.stanleycen.facepunch.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.stanleycen.facepunch.adapter.CardListAdapter;

import java.io.Serializable;

/**
 * Created by scen on 2/17/14.
 */
public abstract class ICardListItem implements Serializable {
    public void onClick() {}
    public abstract boolean isHeader();
    public abstract boolean isSelectable();
    public abstract int getViewType();
    public abstract View getView(LayoutInflater inflater, View convertView, int position, Context context);
}
