package com.stanleycen.facepunch.activities;

import android.app.Activity;
import android.os.Bundle;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.util.Util;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {
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
}
