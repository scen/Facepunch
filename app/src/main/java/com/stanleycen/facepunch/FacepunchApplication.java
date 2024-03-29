package com.stanleycen.facepunch;

import android.app.Application;

import com.stanleycen.facepunch.adapter.ForumPagerAdapter;
import com.stanleycen.facepunch.fragment.HomeFragment;
import com.stanleycen.facepunch.fragment.PopularFragment;
import com.stanleycen.facepunch.fragment.ReadFragment;
import com.stanleycen.facepunch.fragment.SubforumFragment;
import com.stanleycen.facepunch.fragment.ThreadFragment;
import com.stanleycen.facepunch.util.API;

/**
 * Created by scen on 2/11/14.
 */
public class FacepunchApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        API.init(this);

        // register fragments
        ForumPagerAdapter.registerFragmentType(HomeFragment.class);
        ForumPagerAdapter.registerFragmentType(SubforumFragment.class);
        ForumPagerAdapter.registerFragmentType(ThreadFragment.class);
        ForumPagerAdapter.registerFragmentType(PopularFragment.class);
        ForumPagerAdapter.registerFragmentType(ReadFragment.class);

    }
}
