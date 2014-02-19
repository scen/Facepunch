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
import com.stanleycen.facepunch.event.HomeDataEvent;
import com.stanleycen.facepunch.model.fp.FPForum;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.ResponseParser;
import com.stanleycen.facepunch.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/13/14.
 */

public class HomeFragment extends CardListFragment {
    public List<FPForum> forums;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Util.eventBusUnregister(this);
    }

    public void onEventMainThread(HomeDataEvent event) {
        for (FPForum forum : event.forums) {
            cardListAdapter.add(new DefaultTextHeader(forum.name));
            for (FPForum subforum : forum.subforums) {
                cardListAdapter.add(new SubforumCard(subforum, true));
            }
        }
    }

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            forums = (ArrayList<FPForum>) savedInstanceState.getSerializable("forums");
        }
        else {
        }

        Util.eventBusRegister(this);

        if (forums == null) {
            API.addToQueue(new StringRequest("http://facepunch.com", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Util.toast(getActivity(), "Response");
                    Util.executeAsyncTask(new AsyncTask<String, Void, Void>() {
                        @Override
                        protected Void doInBackground(String... params) {
                            forums = ResponseParser.parseHome(params[0]);
                            Util.toast(getActivity(), "Done");
                            EventBus.getDefault().post(new HomeDataEvent((ArrayList<FPForum>) forums));
                            return null;
                        }
                    }, s);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Util.toast(getActivity(), volleyError.toString());
                }
            }));
        }
    }

    public static Bundle makeArgs() {
        Bundle b = new Bundle();
        return b;
    }

    @DebugLog
    public static HomeFragment newInstance(Bundle args) {
        HomeFragment f = new HomeFragment();
        f.setArguments(args);
        return f;
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("forums", (Serializable) forums);

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

    public HomeFragment() {
    }


    @Override
    public String getTitle() {
        return "Home";
    }
}
