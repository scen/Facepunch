package com.stanleycen.facepunch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.adapter.ForumPagerAdapter;
import com.stanleycen.facepunch.fragment.nested.ForumFragment;
import com.stanleycen.facepunch.fragment.nested.ThreadFragment;
import com.stanleycen.facepunch.model.IBackable;
import com.stanleycen.facepunch.model.ITitleable;

import java.util.ArrayList;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/14/14.
 */

public class HomeFragment extends Fragment implements IBackable, ITitleable {
    ViewPager pager;
    ForumPagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, null);
        pager = (ViewPager) root.findViewById(R.id.pager);
        return root;
    }


    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            pagerAdapter.addPage(ThreadFragment.class, null);
            pagerAdapter.addPage(ForumFragment.class, null);
            pagerAdapter.addPage(ForumFragment.class, null);
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
        return "Home";
    }
}
