package com.stanleycen.facepunch.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.espian.showcaseview.OnShowcaseEventListener;
import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.targets.PointTarget;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.adapter.NavDrawerListAdapter;
import com.stanleycen.facepunch.event.ActionBarTitleUpdateEvent;
import com.stanleycen.facepunch.event.PagerPosUpdate;
import com.stanleycen.facepunch.fragment.HomeFragment;
import com.stanleycen.facepunch.fragment.PopularFragment;
import com.stanleycen.facepunch.fragment.ReadFragment;
import com.stanleycen.facepunch.model.IBackable;
import com.stanleycen.facepunch.model.NavDrawerItem;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.Util;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/11/14.
 */

public class MainActivity extends ActionBarActivity {
    SystemBarTintManager tintManager;
    String[] navMenuStrings;
    int pagerPos = 0;

    DrawerLayout drawerLayout;
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

    @DebugLog
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
            case R.id.action_settings:
                onSettingsSelected();
                return true;
            case R.id.action_about:
                onAboutSelected();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doLogout() {
        API.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("nav_selected", navDrawer.getCheckedItemPosition()); // TODO: why the fuck is this -1
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navDrawer = (ListView) findViewById(R.id.navDrawer);

        appTitle = drawerTitle = getTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (Util.isKitKat()) {
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.facepunch_red);
        }

        initNavDrawer(savedInstanceState);
    }

    public void onEventMainThread(PagerPosUpdate update) {
        pagerPos = update.pos;
//        drawerToggle.setDrawerIndicatorEnabled(pagerPos == 0); // wtf why does this crash with VerifyError
        boolean b = pagerPos == 0;
        drawerToggle.setDrawerIndicatorEnabled(b);
    }

    public void onEventMainThread(ActionBarTitleUpdateEvent update) {
        if (update.title != null) {
            setTitle(update.title);
        }
    }

    void initNavDrawer(Bundle savedInstanceState) {
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
            boolean wasEnabled = false;

            @DebugLog
            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(appTitle);
                wasEnabled = drawerToggle.isDrawerIndicatorEnabled();
                drawerToggle.setDrawerIndicatorEnabled(true);
                supportInvalidateOptionsMenu();
            }

            @DebugLog
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                drawerToggle.setDrawerIndicatorEnabled(wasEnabled);
                supportInvalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        if (savedInstanceState == null) {
            onDrawerItemSelect(0);
        }
        else {
            int pos = savedInstanceState.getInt("nav_selected", 0);
            navDrawer.setItemChecked(pos, true);
            navDrawer.setSelection(pos);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    private void showDrawerShowcase() {
        ShowcaseView.ConfigOptions co = new ShowcaseView.ConfigOptions();
        Point size = Util.getScreenSize(getWindowManager().getDefaultDisplay());
        PointTarget target = new PointTarget(0, size.y / 2);
        ShowcaseView sv = ShowcaseView.insertShowcaseView(target, this, null, null, co);
        sv.setOnShowcaseEventListener(new OnShowcaseEventListener() {
            @Override
            public void onShowcaseViewHide(ShowcaseView showcaseView) {

            }

            @Override
            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                Util.setFirstTimeDone(MainActivity.this, "nav_drawer");
            }

            @Override
            public void onShowcaseViewShow(ShowcaseView showcaseView) {

            }
        });
        sv.animateGesture(0, size.y / 2, size.x / 2, size.y / 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.isFirstTime(this, "nav_drawer")) {
            drawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }, 1000);
            showDrawerShowcase();
        }
    }

    void onSettingsSelected() {

    }

    void onAboutSelected() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new PopularFragment();
                break;
            case 2:
                fragment = new ReadFragment();
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
            setTitle(navMenuStrings[pos]);
//            getSupportActionBar().setTitle("aaaaaaaaaaaaaaaaaaaa"); // darn it
            drawerLayout.closeDrawer(navDrawer);
        }
    }
}
