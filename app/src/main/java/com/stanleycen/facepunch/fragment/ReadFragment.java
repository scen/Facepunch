package com.stanleycen.facepunch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.model.IBackable;
import com.stanleycen.facepunch.model.ITitleable;

/**
 * Created by scen on 2/13/14.
 */

public class ReadFragment extends Fragment implements IBackable, ITitleable {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_read, null);
        return root;
    }

    public ReadFragment() {
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public String getTitle() {
        return "Read";
    }
}
