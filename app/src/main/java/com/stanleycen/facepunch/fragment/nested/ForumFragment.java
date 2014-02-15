package com.stanleycen.facepunch.fragment.nested;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.model.ITitleable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/13/14.
 */

@EFragment(R.layout.fragment_forum)
public class ForumFragment extends Fragment implements ITitleable {
    static int meep = 0;
    String name;


    @ViewById
    TextView txtLabel;

    public ForumFragment() {
        name = "Forum View " + (++meep);
    }

    @DebugLog
    @AfterViews
    void afterViews() {
        txtLabel.setText(name);
    }

    @Override
    public String getTitle() {
        return name;
    }
}
