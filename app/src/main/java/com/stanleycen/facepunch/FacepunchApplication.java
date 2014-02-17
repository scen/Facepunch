package com.stanleycen.facepunch;

import android.app.Application;

import com.stanleycen.facepunch.adapter.ForumPagerAdapter;
import com.stanleycen.facepunch.fragment.nested.ForumFragment;
import com.stanleycen.facepunch.fragment.nested.ThreadFragment;
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
        ForumPagerAdapter.registerFragmentType(ForumFragment.class);
        ForumPagerAdapter.registerFragmentType(ThreadFragment.class);
    }
}
