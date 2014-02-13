package com.stanleycen.facepunch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import com.stanleycen.facepunch.R;

import org.androidannotations.annotations.EActivity;

/**
 * Created by scen on 2/11/14.
 */

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
