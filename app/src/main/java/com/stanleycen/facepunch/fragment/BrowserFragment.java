package com.stanleycen.facepunch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.adapter.ForumPagerAdapter;
import com.stanleycen.facepunch.event.OpenSubforumEvent;
import com.stanleycen.facepunch.event.OpenThreadEvent;
import com.stanleycen.facepunch.model.IBackable;
import com.stanleycen.facepunch.model.ITitleable;
import com.stanleycen.facepunch.util.Util;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/14/14.
 */

public class BrowserFragment extends Fragment implements IBackable, ITitleable {
    ViewPager pager;
    ForumPagerAdapter pagerAdapter;

    public static BrowserFragment newInstance(Class clazz, Bundle argsForFirst) {
        BrowserFragment bf = new BrowserFragment();
        Bundle args = new Bundle();
        args.putSerializable("clazz", clazz);
        if (argsForFirst == null) argsForFirst = new Bundle();
        args.putBundle("args_for_first", argsForFirst);
        bf.setArguments(args);
        return bf;
    }

    public void onEventMainThread(OpenSubforumEvent evt) {
        pagerAdapter.addPage(SubforumFragment.class, SubforumFragment.makeArgs(evt.forum));
    }

    public void onEventMainThread(OpenThreadEvent evt) {
        pagerAdapter.addPage(ThreadFragment.class, ThreadFragment.makeArgs(evt.thread));
    }

    public BrowserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_browser, null);
        pager = (ViewPager) root.findViewById(R.id.pager);
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Util.eventBusUnregister(this);
    }

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.eventBusRegister(this);
    }

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
        outState.putInt("page_count", pagerAdapter.getCount());
        outState.putSerializable("fragment_type", pagerAdapter.fragmentType);
    }

    void initAdapter(Bundle bundle) {
        boolean b = false;
        pagerAdapter = new ForumPagerAdapter(getChildFragmentManager(), pager);

        if (bundle == null) {
            // initialize first
            pagerAdapter.addPage((Class) getArguments().getSerializable("clazz"), getArguments().getBundle("args_for_first"));
        }
        else {
            pagerAdapter.count = bundle.getInt("page_count", 0);
            pagerAdapter.fragmentType = (ArrayList<Class>) bundle.getSerializable("fragment_type");
            pagerAdapter.notifyDataSetChanged();
        }
    }

    @DebugLog
    @Override
    public boolean onBackPressed() {
        return pagerAdapter.removeLast();
    }

    @Override
    public String getTitle() {
        return "Facepunch";
    }
}
