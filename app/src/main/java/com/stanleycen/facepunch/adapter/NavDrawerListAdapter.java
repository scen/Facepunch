package com.stanleycen.facepunch.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.stanleycen.facepunch.model.NavDrawerItem;
import com.stanleycen.facepunch.view.NavItemView;

import java.util.ArrayList;

/**
 * Created by scen on 2/12/14.
 */
public class NavDrawerListAdapter extends BaseAdapter {
    Context context;
    ArrayList<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> entries) {
        this.context = context;
        this.navDrawerItems = entries;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public NavDrawerItem getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavItemView navItemView;
        if (convertView == null) {
           navItemView = new NavItemView(context);
        }
        else {
            navItemView = (NavItemView) convertView;
        }

        navItemView.bind(getItem(position));

        return navItemView;
    }
}
