package com.stanleycen.facepunch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.events.LoginResponseEvent;
import com.stanleycen.facepunch.tasks.LoginTask;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.Util;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {
    SystemBarTintManager tintManager;

    @ViewById
    EditText username;

    @ViewById
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (API.isLoggedIn() && false) {
            Intent intent = new Intent(this, MainActivity_.class);
            startActivity(intent);
            finish();
            return;
        }

        if (Util.isKitKat()) {
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.facepunch_red);
        }

        EventBus.getDefault().register(this);
    }

    @Click
    void loginButton() {
        (new LoginTask()).execute(new Pair<String, String>(username.getText().toString(), password.getText().toString()));
    }

    public void onEventMainThread(LoginResponseEvent loginResponseEvent) {
        Toast.makeText(getApplicationContext(), ""+API.isLoggedIn(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
