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

public class ThreadFragment extends Fragment implements ITitleable {
    String name;
    static int meep = 0;
    TextView txtLabel;

    public ThreadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_thread, null);
        txtLabel = (TextView) root.findViewById(R.id.txtLabel);
        return root;
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
        outState.putString("yolo", name);
    }

    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            name = "Thread View " + (++meep);
        }
        else {
            name = savedInstanceState.getString("yolo", "damnit");
        }
        txtLabel.setText(name);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) EventBus.getDefault().post(new ActionBarTitleUpdateEvent(getTitle()));
    }

    @Override
    public String getTitle() {
        return name;
    }
}
