package com.stanleycen.facepunch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.util.Util;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

/**
 * Created by scen on 2/11/14.
 */

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends ActionBarActivity {
    SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Util.isKitKat()) {
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.facepunch_red);
        }
    }

    @OptionsItem(R.id.action_settings)
    void settingsSelected() {

    }

    @OptionsItem(R.id.action_about)
    void aboutSelected() {

    }
}
