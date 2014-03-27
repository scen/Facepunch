package com.stanleycen.facepunch.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanleycen.facepunch.card.PostCard;
import com.stanleycen.facepunch.card.SubforumCard;
import com.stanleycen.facepunch.card.ThreadCard;
import com.stanleycen.facepunch.card.header.DefaultTextHeader;
import com.stanleycen.facepunch.event.SubforumDataEvent;
import com.stanleycen.facepunch.event.ThreadDataEvent;
import com.stanleycen.facepunch.model.fp.FPForum;
import com.stanleycen.facepunch.model.fp.FPPost;
import com.stanleycen.facepunch.model.fp.FPThread;
import com.stanleycen.facepunch.util.Util;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/13/14.
 */

public class ThreadFragment extends CardListFragment {
    public FPThread thread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onEventMainThread(ThreadDataEvent event) {
        if (!event.success || event.toString() == null) {
            Util.toast(getActivity(), "Something went wrong");
            return;
        }
        thread = event.thread;
        for (FPPost post : thread.posts) {
            cardListAdapter.add(new PostCard(post, true));
        }
    }

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Util.eventBusRegister(this);

        if (getArguments() != null && !isRelaunch()) {
            thread = (FPThread) getArguments().getSerializable("thread");
            thread.fetched = false; // TODO omg this is so ghetto
            cardListAdapter.add(new ThreadCard(thread, false));
        }
        else if (savedInstanceState != null) {
            thread = (FPThread) savedInstanceState.getSerializable("thread");
        }
        assert thread != null;
        if (!thread.fetched) {
            thread.fetch();
        }
    }

    public static Bundle makeArgs(FPThread thread) {
        Bundle b = new Bundle();
        b.putSerializable("thread", thread);
        return b;
    }

    @DebugLog
    public static ThreadFragment newInstance(Bundle args) {
        ThreadFragment f = new ThreadFragment();
        f.setArguments(args);
        return f;
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("thread", thread);

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

    public ThreadFragment() {
    }


    @Override
    public String getTitle() {
        return thread.title;
    }
}
