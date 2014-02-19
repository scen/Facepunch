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
import com.stanleycen.facepunch.card.header.DefaultTextHeader;
import com.stanleycen.facepunch.model.fp.FPForum;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && !isArgumentsExpired()) {
            forum = (FPForum) getArguments().getSerializable("forum");
            assert forum != null;
            cardListAdapter.add(new SubforumCard(forum, false));
            forum.fetch(new FPForum.Callback() {
                @Override
                public void onResult(boolean success, FPForum forum) {
                }
            });
        }
        else if (savedInstanceState != null) {
            forum = (FPForum) savedInstanceState.getSerializable("forum");
        }
        assert forum != null;
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

    public SubforumFragment() {
    }


    @Override
    public String getTitle() {
        return forum.name;
    }
}
