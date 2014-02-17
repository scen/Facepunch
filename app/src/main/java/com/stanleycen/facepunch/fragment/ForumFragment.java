package com.stanleycen.facepunch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.adapter.CardListAdapter;
import com.stanleycen.facepunch.card.Card;
import com.stanleycen.facepunch.card.SubforumCard;
import com.stanleycen.facepunch.event.ActionBarTitleUpdateEvent;
import com.stanleycen.facepunch.model.ITitleable;
import com.stanleycen.facepunch.util.Util;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/13/14.
 */

public class ForumFragment extends CardListFragment {
    String name;

    @DebugLog
    public static ForumFragment newInstance(Bundle args) {
        ForumFragment f = new ForumFragment();
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
            name = "Forum View";
        }
        else {
            name = savedInstanceState.getString("yolo", "damnit");
        }
    }

    public ForumFragment() {
    }


    @Override
    public String getTitle() {
        return name;
    }
}
