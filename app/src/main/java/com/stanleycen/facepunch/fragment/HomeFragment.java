package com.stanleycen.facepunch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.adapter.ForumPagerAdapter;
import com.stanleycen.facepunch.event.ActionBarTitleUpdateEvent;
import com.stanleycen.facepunch.fragment.nested.ForumFragment_;
import com.stanleycen.facepunch.model.IBackable;
import com.stanleycen.facepunch.model.ITitleable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/14/14.
 */

@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment implements IBackable, ITitleable {
    ForumPagerAdapter pagerAdapter;

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @ViewById
    ViewPager pager;

    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter(savedInstanceState);
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("yolo", true);
    }

    void initAdapter(Bundle bundle) {
        boolean b = false;
        pagerAdapter = new ForumPagerAdapter(getChildFragmentManager(), pager);
        if (bundle == null) {
            pagerAdapter.addPage(new ForumFragment_());
            pagerAdapter.addPage(new ForumFragment_());
            pagerAdapter.addPage(new ForumFragment_());
        }
    }

    @DebugLog
    @Override
    public boolean onBackPressed() {
        return pagerAdapter.removeLast();
    }

    @Override
    public String getTitle() {
        return "Home";
    }
}
