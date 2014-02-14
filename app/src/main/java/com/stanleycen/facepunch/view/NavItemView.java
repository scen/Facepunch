package com.stanleycen.facepunch.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.model.NavDrawerItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by scen on 2/13/14.
 */

@EViewGroup(R.layout.nav_list_item)
public class NavItemView extends RelativeLayout {
    @ViewById
    TextView title;

    @ViewById
    ImageView icon;

    public NavItemView(Context context) {
        super(context);
    }

    public void bind(NavDrawerItem navDrawerItem) {
        title.setText(navDrawerItem.getTitle());
        icon.setImageResource(navDrawerItem.getIcon());
    }
}
