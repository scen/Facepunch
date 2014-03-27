package com.stanleycen.facepunch.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stanleycen.facepunch.card.SubforumCard;
import com.stanleycen.facepunch.card.ThreadCard;
import com.stanleycen.facepunch.card.header.DefaultTextHeader;
import com.stanleycen.facepunch.event.SubforumDataEvent;
import com.stanleycen.facepunch.model.fp.FPForum;
import com.stanleycen.facepunch.model.fp.FPThread;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.ResponseParser;
import com.stanleycen.facepunch.util.Util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/13/14.
 */

public class SubforumFragment extends CardListFragment {
    public FPForum forum;
    public String fuck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onEventMainThread(SubforumDataEvent event) {
        if (!event.success || event.forum == null) {
            Util.toast(getActivity(), "Something went wrong");
            return;
        }
        if (forum != event.forum) { // not the correct forum update
            return;
        }
        forum = event.forum;
        if (!forum.subforums.isEmpty()) {
            cardListAdapter.add(new DefaultTextHeader("Subforums"));
            for (FPForum subforum : forum.subforums) {
                cardListAdapter.add(new SubforumCard(subforum, true));
            }
        }
        cardListAdapter.add(new DefaultTextHeader("Threads"));
        for (FPThread thread : forum.threads) {
            cardListAdapter.add(new ThreadCard(thread, true));
        }
    }

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Util.eventBusRegister(this);

        if (getArguments() != null && !isRelaunch()) {
            forum = (FPForum) getArguments().getSerializable("forum");
            forum.fetched = false; // TODO omg this is so ghetto
            cardListAdapter.add(new SubforumCard(forum, false));
        }
        else if (savedInstanceState != null) {
            forum = (FPForum) savedInstanceState.getSerializable("forum");
        }
        assert forum != null;
        if (!forum.fetched) {
            forum.fetch();
        }
    }

    public static Bundle makeArgs(FPForum forum) {
        Bundle b = new Bundle();
        b.putSerializable("forum", forum);
        return b;
    }

    @DebugLog
    public static SubforumFragment newInstance(Bundle args) {
        SubforumFragment f = new SubforumFragment();
        f.setArguments(args);
        return f;
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("forum", forum);

    }

    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
        }
        else {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Util.eventBusUnregister(this);
    }

    public SubforumFragment() {
    }


    @Override
    public String getTitle() {
        return forum.name;
    }
}
