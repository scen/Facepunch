package com.stanleycen.facepunch.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by scen on 2/16/14.
 */
public interface ICardItem {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView, int position, Context context);
    public boolean isEnabled();
}
