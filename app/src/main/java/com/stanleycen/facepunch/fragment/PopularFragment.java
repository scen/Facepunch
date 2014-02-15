package com.stanleycen.facepunch.fragment;

import android.support.v4.app.Fragment;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.model.IBackable;
import com.stanleycen.facepunch.model.ITitleable;

import org.androidannotations.annotations.EFragment;

/**
 * Created by scen on 2/13/14.
 */

@EFragment(R.layout.fragment_popular)
public class PopularFragment extends Fragment implements IBackable, ITitleable {
    public PopularFragment() {
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public String getTitle() {
        return "Popular";
    }
}
