package com.stanleycen.facepunch.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.model.NavDrawerItem;

/**
 * Created by scen on 2/13/14.
 */

public class NavItemView extends RelativeLayout {
    TextView title;
    ImageView icon;

    public NavItemView(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.nav_list_item, this);
        title = (TextView) findViewById(R.id.title);
        icon = (ImageView) findViewById(R.id.icon);
    }

    public void bind(NavDrawerItem navDrawerItem) {
        title.setText(navDrawerItem.getTitle());
        icon.setImageResource(navDrawerItem.getIcon());
    }
}
