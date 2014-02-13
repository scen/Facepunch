package com.stanleycen.facepunch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.adapters.NavDrawerListAdapter;
import com.stanleycen.facepunch.models.NavDrawerItem;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.Util;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/11/14.
 */

//@EActivity(R.layout.activity_main)
//@OptionsMenu(R.menu.menu_main)
public class MainActivity extends ActionBarActivity {
    SystemBarTintManager tintManager;

//    @ViewById
    DrawerLayout drawerLayout;

//    @ViewById
    ListView navDrawer;

    CharSequence drawerTitle;
    CharSequence appTitle;

    ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
    NavDrawerListAdapter navDrawerListAdapter;

    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        appTitle = title;
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @DebugLog
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_logout:
                doLogout();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doLogout() {
        API.logout();
        Intent intent = new Intent(this, LoginActivity_.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        appTitle = drawerTitle = getTitle();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navDrawer = (ListView) findViewById(R.id.navDrawer);

        String[] navMenuStrings = getResources().getStringArray(R.array.nav_drawer_strings);
        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        assert navMenuStrings.length == navMenuIcons.length();

        for (int i = 0; i < navMenuStrings.length; i++) {
            navDrawerItems.add(new NavDrawerItem(navMenuStrings[i], navMenuIcons.getResourceId(i, -1)));
        }

        navMenuIcons.recycle();

        navDrawerListAdapter = new NavDrawerListAdapter(this, navDrawerItems);
        navDrawer.setAdapter(navDrawerListAdapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_navigation_drawer, R.string.app_name, R.string.app_name) {
            @DebugLog
            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(appTitle);
                supportInvalidateOptionsMenu();
            }

            @DebugLog
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                supportInvalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        if (Util.isKitKat()) {
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.facepunch_red);
        }

        if (savedInstanceState == null) {

        }
        else {

        }

//        EventBus.getDefault().register(this);
    }

//    @OptionsItem(R.id.action_settings)
    void settingsSelected() {

    }

//    @OptionsItem(R.id.action_about)
    void aboutSelected() {

    }
}
