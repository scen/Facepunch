package com.stanleycen.facepunch.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.adapter.NavDrawerListAdapter;
import com.stanleycen.facepunch.event.ActionBarTitleUpdateEvent;
import com.stanleycen.facepunch.event.PagerPosUpdate;
import com.stanleycen.facepunch.fragment.HomeFragment_;
import com.stanleycen.facepunch.fragment.PopularFragment_;
import com.stanleycen.facepunch.fragment.ReadFragment_;
import com.stanleycen.facepunch.model.IBackable;
import com.stanleycen.facepunch.model.NavDrawerItem;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/11/14.
 */

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends ActionBarActivity {
    SystemBarTintManager tintManager;
    String[] navMenuStrings;
    int pagerPos = 0;

    @ViewById
    DrawerLayout drawerLayout;

    @ViewById
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
        drawerTitle = title;
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
        if (drawerToggle.isDrawerIndicatorEnabled() && drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
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
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        // find current fragment
        IBackable curFrag = (IBackable) fm.findFragmentByTag("last_frag");

        if (curFrag != null) {
            if (curFrag.onBackPressed()) return; //handled
        }

        new AlertDialog.Builder(this).setMessage("Are you sure you want to quit Facepunch?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                }).setNegativeButton(android.R.string.no, null).show();
    }

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appTitle = drawerTitle = getTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (Util.isKitKat()) {
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.facepunch_red);
        }

        if (savedInstanceState == null) {

        }
        else {

        }

        EventBus.getDefault().register(this);
    }

    public void onEvent(PagerPosUpdate update) {
        pagerPos = update.pos;
//        drawerToggle.setDrawerIndicatorEnabled(pagerPos == 0); // wtf why does this crash with VerifyError
        boolean b = pagerPos == 0;
        drawerToggle.setDrawerIndicatorEnabled(b);
    }

    public void onEvent(ActionBarTitleUpdateEvent update) {
        if (update.title != null) {
            setTitle(update.title);
        }
    }

    @AfterViews
    void initNavDrawer() {
        navMenuStrings = getResources().getStringArray(R.array.nav_drawer_strings);
        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        assert navMenuStrings.length == navMenuIcons.length();

        for (int i = 0; i < navMenuStrings.length; i++) {
            navDrawerItems.add(new NavDrawerItem(navMenuStrings[i], navMenuIcons.getResourceId(i, -1)));
        }

        navMenuIcons.recycle();

        navDrawerListAdapter = new NavDrawerListAdapter(this, navDrawerItems);
        navDrawer.setAdapter(navDrawerListAdapter);
        navDrawer.setOnItemClickListener(new DrawerItemClickListener());

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
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        onDrawerItemSelect(0);
    }

    @OptionsItem(R.id.action_settings)
    void settingsSelected() {

    }

    @OptionsItem(R.id.action_about)
    void aboutSelected() {

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            onDrawerItemSelect(position);
        }
    }

    private void onDrawerItemSelect(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                fragment = new HomeFragment_();
                break;
            case 1:
                fragment = new PopularFragment_();
                break;
            case 2:
                fragment = new ReadFragment_();
            default:
                break;
        }

        FragmentManager fm = getSupportFragmentManager();

        // clear backstack
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if (fragment != null) {
            fm.beginTransaction()
                    .replace(R.id.contentFrame, fragment, "last_frag").addToBackStack("last_frag").commit();
            navDrawer.setItemChecked(pos, true);
            navDrawer.setSelection(pos);
//            setTitle(navMenuStrings[pos]);
            drawerLayout.closeDrawer(navDrawer);
        }
    }
}
