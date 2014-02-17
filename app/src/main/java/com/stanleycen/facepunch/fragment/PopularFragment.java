package com.stanleycen.facepunch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.event.ActionBarTitleUpdateEvent;
import com.stanleycen.facepunch.model.IBackable;
import com.stanleycen.facepunch.model.ITitleable;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/13/14.
 */

public class PopularFragment extends CardListFragment {
    String name;

    @DebugLog
    public static PopularFragment newInstance(Bundle args) {
        PopularFragment f = new PopularFragment();
        f.setArguments(args);
        return f;
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("yolo", name);
    }

    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            name = "Popular View";
        }
        else {
            name = savedInstanceState.getString("yolo", "damnit");
        }
    }

    public PopularFragment() {
    }


    @Override
    public String getTitle() {
        return name;
    }
}
