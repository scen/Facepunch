package com.stanleycen.facepunch.fragment;

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

    @ViewById
    ViewPager pager;

    @AfterViews
    void initAdapter() {

        pagerAdapter = new ForumPagerAdapter(getChildFragmentManager(), pager);
        pagerAdapter.addPage(new ForumFragment_());
        pagerAdapter.addPage(new ForumFragment_());
        pagerAdapter.addPage(new ForumFragment_());

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
