package com.stanleycen.facepunch.fragment.nested;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.event.ActionBarTitleUpdateEvent;
import com.stanleycen.facepunch.model.ITitleable;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/13/14.
 */

public class ForumFragment extends Fragment implements ITitleable {
    static int meep = 0;
    TextView txtLabel;
    String name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_forum, null);
        txtLabel = (TextView) root.findViewById(R.id.txtLabel);
        return root;
    }


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

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) EventBus.getDefault().post(new ActionBarTitleUpdateEvent(getTitle()));
    }

    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            name = "Forum View " + (++meep);
        }
        else {
            name = savedInstanceState.getString("yolo", "damnit");
        }
        txtLabel.setText(name);
    }

    public ForumFragment() {
    }


    @Override
    public String getTitle() {
        return name;
    }
}
