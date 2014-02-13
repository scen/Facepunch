package com.stanleycen.facepunch.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.models.NavDrawerItem;
import com.stanleycen.facepunch.views.NavItemView;
import com.stanleycen.facepunch.views.NavItemView_;

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
           navItemView = NavItemView_.build(context);
        }
        else {
            navItemView = (NavItemView) convertView;
        }

        navItemView.bind(getItem(position));

        return navItemView;
    }
}